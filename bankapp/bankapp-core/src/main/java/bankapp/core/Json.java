package bankapp.core;

import org.json.simple.JSONObject;

public class Json {

    public static void main(String[] args) {

        JSONObject file = new JSONObject();

        file.put("Full Name", "Ritu Sharma");
        file.put("Roll No.", 1234567);
        file.put("Tuition Fees", 123456);

        // To print in JSON format.
        System.out.print(file);
    }
}
