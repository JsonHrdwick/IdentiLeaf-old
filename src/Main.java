import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try (Connection con = DriverManager.getConnection(creds.databaseid, creds.dbusername, creds.dbpassword)) {

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
        }
    }
}