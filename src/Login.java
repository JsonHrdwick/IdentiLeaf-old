import java.util.HashMap;
import java.sql.*;

public class Login {
    private Connection sqldb;

    public Login(Connection conn) {
        sqldb = conn;
    }
    public boolean registerUser(String email, String password) throws SQLException {
        if (!validateEmail(email) && validateNewPassword(password)){
            // Register code here
            generatePasswordHash(password);
            return true;
        }
        return false;
    }
    public boolean loginUser(String email, String password) throws SQLException {
        if (validateEmail(email)){
            String passwordOld = userLoginInfo(email).get(email); // returns password to given email
            if (validateExistingPassword(passwordOld, password)){
                // update login history here
                return true;
            }
        }
        return false;
    }
    private boolean validateEmail(String email) throws SQLException {
        Statement stmt = sqldb.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT email FROM mydb.User WHERE email = '" + email + "'");
        if (rs.getString("email").equals(email)){
            return true;
        }
        // Check email exists or not
        return false;
    }
    private boolean validateNewPassword(String password) {
        // Regex new password
        return true;
    }
    private boolean validateExistingPassword(String passwordOld, String passwordNew) {
        // check hash match on passwords
        if (generatePasswordHash(passwordOld).equals(passwordNew)){
            return true;
        }
        return false;
    }
    private String generatePasswordHash(String password) {
        // Create password hash
        String passHash = password;
        return passHash;
    }
    private HashMap<String, String> userLoginInfo(String email) throws SQLException {
        Statement stmt = sqldb.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT email, passwordHASH FROM mydb.User WHERE email = '" + email + "'");

        HashMap<String, String> userInfo = new HashMap<>();
        userInfo.put(rs.getString("email"), rs.getString("passwordHash"));
        return userInfo;
    }
}
