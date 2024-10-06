import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Query {
    private HashMap<Integer,HashMap<String,String[]>> questionMap;
    private String question;
    private Integer questionNumber = 0;

    Query() throws FileNotFoundException {
        questionMap = generateQuestions();
    }

    // Implement Database pull
    private HashMap<Integer,HashMap<String,String[]>> generateQuestions() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("questions.csv"));
        HashMap<Integer,HashMap<String,String[]>> questionMap = new HashMap<>();
        int questionId = 0;
        while(scanner.hasNext()){
            // Takes line from csv and splits it into an array
            String line = scanner.nextLine();
            String[] tokens = line.split(",");
            // Adds ID to Map
            questionMap.put(questionId, new HashMap<>());
            // Array for the answers
            String[] answers = new String[tokens.length - 1];
            for(int i = 1; i < tokens.length; i++){
                answers[i-1] = tokens[i];
            }
            // Adds Question, Answers to the ID Map
            questionMap.get(questionId).put(tokens[0], answers);
            questionId++;
        }
        scanner.close();
        return questionMap;
    }

    // Implement question pull
    public String promptQuestion(){
        HashMap<String,String[]> innerMap = questionMap.get(questionNumber);
        question = innerMap.keySet().toString();
        questionNumber++;
        return question;
    }

    // Implement answer adjustment
    public void resolveAnswer(boolean a){
        if (!a && !questionMap.containsKey(questionNumber)) {
            questionNumber--;
            promptQuestion();
        }
    }

    // Validate output confidence
    public boolean checkConfidence(){
        if (questionMap.size() == 1){
            return true;
        } else {
            return false;
        }
    }

    // Returns answers as Strings and not SQL style
    public String getAnswers(){
        String[] answers = questionMap.get(questionNumber-1).get(question);
        String answer = "";
        if (answers == null){ return answer; }
        for (int i = 0; i <= answers.length; i++){
            if ((i+1)%2 == 0) {
                answer += answers[i];
            }
            if (i != answers.length-1){
                answer += ",";
            }
        }
        return answer;
    }

    // AI API call
    public String callAI(String callString){
        return callString;
    }
}
