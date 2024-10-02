import java.util.HashMap;

public class Query {
    private HashMap<String,String> questionMap;

    Query(){
        questionMap = generateQuestions();
    }

    // Implement Database pull
    private HashMap<String,String> generateQuestions(){
        HashMap<String,String> questionMap = new HashMap<>();
        return questionMap;
    }

    // Implement question pull
    public String promptQuestion(){
        return "";
    }

    // Implement answer adjustment
    public void resolveAnswer(boolean a){
        if (a){
            a=true;
        } else {
           a=false;
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

    // AI API call
    public String callAI(String callString){
        return callString;
    }
}
