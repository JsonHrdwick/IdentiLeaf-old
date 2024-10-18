import java.sql.*;
import java.util.Scanner;


public class Main {

    static String dbid;
    static String dbusername;
    static String dbpassword;
    static String aikey;

    public static void main(String[] args) throws Exception {

        credFileInit("creds.json");
        Query Q = new Query();

        String initQuery = """
                select scientificName, commonName, BarkType.type, LeafType.type, PlantType.type from mydb.Tree
                inner join mydb.BarkType
                on Tree.BarkType_ID = BarkType.ID
                inner join mydb.LeafType
                on Tree.LeafType_ID = LeafType.ID
                inner join mydb.PlantType
                on Tree.PlantType_ID = PlantType.ID""";

        while (!Q.checkConfidence()) {

            try (Connection con = DriverManager.getConnection(dbid, dbusername, dbpassword)) {

                Statement stmt = con.createStatement();

                System.out.println(initQuery);
                ResultSet rs = stmt.executeQuery(initQuery);

                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("commonName") +
                            "; Plant Type: " + rs.getString("PlantType.type") +
                            "; Leaf Type: " + rs.getString("LeafType.type") +
                            "; Bark Type: " + rs.getString("BarkType.type"));
                }
                System.out.println("Hello and welcome!");

                String question = Q.promptQuestion();
                String answers = Q.getAnswers();
                String userAnswer = userAnswer(question, answers);
                if (Q.questionNumber == 0){
                    initQuery += " where ";
                }
                initQuery += Q.resolveAnswer(userAnswer);
            }
        }
        // COMMENTED OUT TO NOT WASTE API CALLS
        //System.out.println(AI.callAI("Give me more information on the White Oak Tree", aikey));
    }
    public static String userAnswer(String question,String answers){
        String wholeAnswer = answers.replace(","," or ");
        System.out.println(question);
        System.out.println(wholeAnswer);
        Scanner scanner = new Scanner(System.in);
        String userInputAnswer="";
        userInputAnswer = scanner.nextLine();
        return userInputAnswer;
    }
    public static void credFileInit(String filename) throws Exception {
        FileParser fp = new FileParser(filename);
        dbid = fp.getDatabaseid();
        dbusername = fp.getDbusername();
        dbpassword = fp.getDbpassword();
        aikey = fp.getAikey();

    }
}