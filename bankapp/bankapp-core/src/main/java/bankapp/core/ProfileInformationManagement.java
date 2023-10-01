package bankapp.core;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ProfileInformationManagement {

        private JSONArray jsonArray = new JSONArray();

        public void writeToFile(Profile profile, String fileName) throws IOException, ParseException {
                String name = profile.getName();
                String email = profile.getEmail();
                String tlf = profile.getTlf();
                String password = profile.getPassword();

                FileWriter writer = new FileWriter(fileName, false);

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("Name: ", name);
                jsonObject.put("Email: ", email);
                jsonObject.put("Tlf: ", tlf);
                jsonObject.put("Password: ", password);

                getJsonArray().add(jsonObject);

                writer.write(getJsonArray().toJSONString());

                writer.close();

                System.out.println("Succesfully created");

        }

        public JSONArray getJsonArray() {
                return jsonArray;
        }

        public void readFromFile(FileReader reader) {

        }

        public static void main(String[] args) throws IOException, ParseException {

                ProfileInformationManagement management = new ProfileInformationManagement();
                Profile profile1 = new Profile("Klein Cornolis", "Klein@gmail.com", "12345678",
                                "JegHeterAliceOgErRasist123");
                Profile profile2 = new Profile("Philip Vu Lam", "Philip@gmail.com",
                                "87654321",
                                "JegHeterAliceOgErRasist246");

                management.writeToFile(profile1,
                                "bankapp/bankapp-core/src/main/java/bankapp/Files/ProfileInformation.json");
                System.out.println(management.getJsonArray());
                management.writeToFile(profile2,
                                "bankapp/bankapp-core/src/main/java/bankapp/Files/ProfileInformation.json");
                System.out.println(management.getJsonArray());

        }

}
