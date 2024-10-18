import java.sql.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws Exception {

        FileParser fp = new FileParser("creds.json");
        String dbid = fp.getDatabaseid();
        String dbusername = fp.getDbusername();
        String dbpassword = fp.getDbpassword();
        String aikey = fp.getAikey();

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
                ResultSet rs = stmt.executeQuery(initQuery);
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("commonName") +
                            "; Plant Type: " + rs.getString("PlantType.type") +
                            "; Leaf Type: " + rs.getString("LeafType.type") +
                            "; Bark Type: " + rs.getString("BarkType.type"));
                }

                String userAnswer = Q.userAnswer(Q.promptQuestion(), Q.getAnswers());
                if (Q.questionNumber == 0){
                    initQuery += " where ";
                }
                initQuery += Q.resolveAnswer(userAnswer);
            }
        }
        // COMMENTED OUT TO NOT WASTE API CALLS
        //System.out.println(AI.callAI("Give me more information on the White Oak Tree", aikey));
    }

}