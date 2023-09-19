package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private String tlf;
    private String password;
    private List<Account> accounts = new ArrayList<>();

    public Profile(String name, String email, String tlf, String password) {
        if ((name.contains(" "))) {
            String[] splits = name.split(" ");
            // System.out.println(splits);
            for (int i = 0; i < splits.length; i++) {
                for (int j = 0; j < splits[i].length(); j++) {
                    if (!Character.isLetter(splits[i].strip().charAt(j))) {
                        throw new IllegalArgumentException("Name can only contain letters");
                    }
                }
            }
        }

        else {
            throw new IllegalArgumentException("Name must contain both surname and last name");
        }

        if (password.length() < 8) {
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        }

        if (tlf.length() != 8 || !isNumeric(tlf)) {
            throw new IllegalArgumentException("Invalid phonenumber");
        }

        this.name = name;
        this.email = email;
        this.tlf = tlf;
        this.password = password;
        System.out.println("Your profile was made successfully!");
    }

    public static boolean isNumeric(String test) {
        try {
            Double.parseDouble(test);
            return true;
        }

        catch (NumberFormatException e) {
            return false;
        }
    }

    public void createAccount(String name) {
        accounts.add(new Account(name));
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getTlf() {
        return tlf;
    }

    public String getPassword() {
        return password;
    }

    // Legg til get-er for account-listen

    public static void main(String[] args) {
        Profile profile = new Profile("Klein  C", null, "j2345678", "asdf1234567@¨+0");
    }
}
