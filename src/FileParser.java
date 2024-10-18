import com.google.gson.Strictness;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class FileParser {
    private String databaseid;
    private String dbusername;
    private String dbpassword;
    private String aikey;

    public FileParser(String filename) throws IOException {
        String json = fileReader("creds.json");

        JsonReader jsonReading = new JsonReader( new StringReader(json));
        jsonReading.setStrictness(Strictness.LENIENT);
        jsonReading.beginObject();

        jsonReading.nextName();
        this.databaseid = jsonReading.nextString();
        jsonReading.nextName();
        this.dbusername = jsonReading.nextString();
        jsonReading.nextName();
        this.dbpassword = jsonReading.nextString();
        jsonReading.nextName();
        this.aikey = jsonReading.nextString();
    }

    public String getDatabaseid() {
        return databaseid;
    }

    public String getDbusername() {
        return dbusername;
    }

    public String getDbpassword() {
        return dbpassword;
    }

    public String getAikey() {
        return aikey;
    }

    private String fileReader(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        String output = scanner.next();
        while (scanner.hasNextLine()) {
            output += scanner.nextLine();
        }
        scanner.close();
        return output;
    }
}
