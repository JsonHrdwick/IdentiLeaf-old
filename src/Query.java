import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Query {
    private HashMap<Integer,HashMap<String,ArrayList<String>>> questionMap;
    private String question;
    private Integer questionNumber = 0;

    Query() throws FileNotFoundException {
        questionMap = generateQuestions();
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
        ArrayList<String> answers = questionMap.get(questionNumber-1).get(question);
        String answer = "";
        if (answers == null){ return answer; }
        for (int i = 0; i < answers.size(); i+=2){
            answer += answers.get(i);
            if (i < answers.size() - 2){
                answer += ",";
            }
        }
        return answer;
    }

    // AI API call
    public static String callAI(String callString, String aikey) throws Exception {
        String apiKey = aikey;
        String message = callString;
        String url = "https://api.openai.com/v1/chat/completions";
        String model = "gpt-3.5-turbo";
        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // returns the extracted contents of the response.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
   private static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        return response.substring(startMarker, endMarker); // Returns the substring containing only the response.
    }
}