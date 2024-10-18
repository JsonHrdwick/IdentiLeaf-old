import java.sql.*;
import java.util.Objects;

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

        String foundTree = "";
        int treeCount = 2;
        while (treeCount > 1) {

            try (Connection con = DriverManager.getConnection(dbid, dbusername, dbpassword)) {

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(initQuery);
                treeCount=0;
                while (rs.next()) {
                    System.out.println("Name: " + rs.getString("commonName") +
                            "; Plant Type: " + rs.getString("PlantType.type") +
                            "; Leaf Type: " + rs.getString("LeafType.type") +
                            "; Bark Type: " + rs.getString("BarkType.type"));
                    treeCount++;
                }
                // Exit if found tree
                if (treeCount == 1) {
                    rs = stmt.executeQuery(initQuery);
                    rs.next();
                    foundTree = rs.getString("commonName");
                    break;
                }
                // Restart if no tree
                if (treeCount == 0) {
                   System.out.println("Something went wrong. Restarting");
                   main(null);
                   break;
                }

                // Prompt Question and Resolve Answer
                String userAnswer = Q.userAnswer(Q.promptQuestion(), Q.getAnswers());
                if (Q.questionNumber == 0){
                    initQuery += " where ";
                }
                initQuery += Q.resolveAnswer(userAnswer);
            }
        }

        if (Objects.equals(Q.userAnswer("Is " + foundTree + " your tree?", "yes or no"), "yes")){
            String aiQuestion = "Give me more information on the " + foundTree;
            // COMMENTED OUT TO NOT WASTE API CALLS
            System.out.println(AI.callAI(aiQuestion, aikey));
        } else {
            System.out.println("Something went wrong. Restarting");
            main(null);
        }

    }

}