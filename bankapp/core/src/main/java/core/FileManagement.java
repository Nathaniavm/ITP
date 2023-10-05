package bankapp.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileManagement {

    /**
     * Writes information about profile to given file.
     * 
     * @param profile  - Profile to write about
     * @param fileName - filepath of file to write to
     * @throws IOException
     */
    public static void writeToFile(Profile profile, String fileName) throws IOException {
        String name = profile.getName();
        String email = profile.getEmail();
        String tlf = profile.getTlf();
        String password = profile.getPassword();
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));

        bw.write((name + "," + email + "," + tlf + "," + password));
        bw.newLine();
        bw.close();
    }

    /**
     * Reads information from file
     * 
     * @param inputStream - file to read from
     * @throws IOException
     */
    public static void readFromFile(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        inputStream.close();
    }

    /**
     * Read from a file looking for a specific name
     * 
     * @param wantedName  - name to look for
     * @param inputStream - file to read from
     * @throws IOException
     */
    public static void readFromFileBasedName(String wantedName, InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        boolean check = false;
        while ((line = reader.readLine()) != null) {
            String[] infos = line.split(",");
            if (infos[0].equals(wantedName)) {
                System.out.println(line);
                check = true;
            }

        }
        if (!check) {
            System.out.println("This person was not found.");
        }
        inputStream.close();
    }

    // temporary testing
    public static void main(String[] args) throws FileNotFoundException {
        Profile prof1 = new Profile("Nam Namu", "NamNamu@gmail.com", "41174086", "tshodkdjsh");
        Profile prof2 = new Profile("Philip Lam", "PhilipLam@gmail.com", "78901234", "tshodkdjsh");
        String os = ("bankapp/bankapp-core/src/main/java/bankapp/Files/PersonalInformations.txt");
        InputStream is = new FileInputStream(
                "bankapp/bankapp-core/src/main/java/bankapp/Files/PersonalInformations.txt");
        InputStream is1 = new FileInputStream(
                "bankapp/bankapp-core/src/main/java/bankapp/Files/PersonalInformations.txt");
        try {
            writeToFile(prof1, os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            writeToFile(prof2, os);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            readFromFile(is);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            readFromFileBasedName("Philip Lam", is1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
