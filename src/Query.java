import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class Query {
    private HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap;
    private String question;
    public Integer questionNumber;

    Query() throws FileNotFoundException {
        questionMap = generateQuestions();
        questionNumber = 0;
    }

    // Implement Database pull
    private HashMap<Integer,HashMap<String,ArrayList<String>>> generateQuestions() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("questions.csv"));
        HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap = new HashMap<>();
        int questionId = 0;
        while(scanner.hasNext()){
            // Takes line from csv and splits it into an array
            String line = scanner.nextLine();
            String[] tokens = line.split(",");
            // Adds ID to Map
            questionMap.put(questionId, new HashMap<>());
            // Array for the answers
            ArrayList<String> answers = new ArrayList<>(tokens.length - 1);
            for(int i = 1; i < tokens.length; i++){
                answers.add(i - 1, tokens[i]);
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
        HashMap<String,ArrayList<String>> innerMap = questionMap.get(questionNumber);
        question = innerMap.keySet().toString().replace("[", "").replace("]", "");
        return question;
    }

    // Implement answer adjustment
    public String resolveAnswer(String answer){
        HashMap<String,ArrayList<String>> innerMap = questionMap.get(questionNumber);
        ArrayList<String> answers = innerMap.get(question);
        int SQLIndex = answers.indexOf(answer) + 1;
        questionNumber++;
        return answers.get(SQLIndex);
    }

    // Returns answers as Strings and not SQL style
    public String getAnswers(){
        ArrayList<String> answers = questionMap.get(questionNumber).get(question);
        String answer = "";
        if (answers == null){ return answer; }
        for (int i = 0; i < answers.size(); i+=2){
            answer += answers.get(i);
            if (i < answers.size() - 2){
                answer += " or ";
            }
        }
        return answer;
    }
    public static String userAnswer(String question,String answers) {
        System.out.println(question);
        System.out.println(answers);
        Scanner scanner = new Scanner(System.in);
        String userInputAnswer = "";
        userInputAnswer = scanner.nextLine();
        return userInputAnswer;
    }

}