import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.sql.*;
import java.util.Scanner;


public class Main {

    static String databaseid;
    static String dbusername;
    static String dbpassword;
    static String aikey;

    public static void main(String[] args) throws SQLException, IOException, FileNotFoundException {

        jsonReader();
        Query Q = new Query();

        String question = Q.promptQuestion();
        String answers = Q.getAnswers();
        String userAnswer = userAnswer(question, answers);

        try (Connection con = DriverManager.getConnection(databaseid, dbusername, dbpassword)) {

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select scientificName, commonName, BarkType.type, LeafType.type, PlantType.type from mydb.Tree\n" +
                    "inner join mydb.BarkType\n" +
                    "on Tree.BarkType_ID = BarkType.ID\n" +
                    "inner join mydb.LeafType\n" +
                    "on Tree.LeafType_ID = LeafType.ID\n" +
                    "inner join mydb.PlantType\n" +
                    "on Tree.PlantType_ID = PlantType.ID");

            while (rs.next()) {
                System.out.println("Name: " + rs.getString("commonName") +
                        "; Plant Type: " + rs.getString("PlantType.type") +
                        "; Leaf Type: " + rs.getString("LeafType.type") +
                        "; Bark Type: " + rs.getString("BarkType.type"));
            }
            System.out.println("Hello and welcome!");

        }

        // COMMENTED OUT TO NOT WASTE API CALLS
        //System.out.println(Query.callAI("Give me more information on the White Oak Tree", aikey));
    }

    public static void jsonReader() throws IOException {
        String json = fileReader("creds.json");

        JsonReader jsonReader = new JsonReader( new StringReader(json));
        jsonReader.setStrictness(Strictness.LENIENT);
        jsonReader.beginObject();

        jsonReader.nextName();
        databaseid = jsonReader.nextString();
        jsonReader.nextName();
        dbusername = jsonReader.nextString();
        jsonReader.nextName();
        dbpassword = jsonReader.nextString();
        jsonReader.nextName();
        aikey = jsonReader.nextString();

    }
    public static String fileReader(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        String output = scanner.next();
        while (scanner.hasNextLine()) {
            output += scanner.nextLine();
        }
        return output;
    }
    public static String userAnswer(String question,String answers){
        String wholeAnswer = answers.replace(","," or ");
        System.out.println(question);
        System.out.println(wholeAnswer);
        Scanner scanner = new Scanner(System.in);
        String userAnswer = scanner.nextLine();
        scanner.close();
        return userAnswer;
    }
}