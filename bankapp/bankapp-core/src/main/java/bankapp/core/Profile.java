package bankapp.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private String tlf;
    private String password;
    private List<Account> accounts = new ArrayList<>();
    private ArrayList<String> landskoder = new ArrayList<>(Arrays.asList("ad", "ae", "af", "ag", "ai", "al", "am", "ao",
            "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl",
            "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci",
            "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz",
            "ec", "ee", "eg", "eh", "er", "es", "et", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf",
            "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr",
            "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg",
            "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu",
            "lv", "ly", "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms",
            "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu",
            "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re",
            "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so",
            "sr", "ss", "st", "sv", "sx", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to",
            "tr", "tt", "tv", "tw", "tz", "ua", "ug", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu",
            "wf", "ws", "ye", "yt", "za", "zm", "zw"));

    public Profile(String name, String email, String tlf, String password) {
        if ((name.contains(" "))) {
            String[] splits = name.split(" ");
            System.out.println(splits);
            for (int i = 0; i < splits.length; i++) {
                for (int j = 0; j < splits[i].length(); j++) {
                    if (!Character.isLetter(splits[i].strip().charAt(j))) {
                        throw new IllegalArgumentException("Name can only contain letters");
                    }
                }
            }
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

    private static boolean isNumeric(String test) {
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

    /*
     * public void changeEmail(String email) {
     * if (validEmail(email)) {
     * this.email = email;
     * }
     * }
     */

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
        Profile profile = new Profile("Klein  C", "klein.cornolis@gmail.com", "12345678", "asdf1234567@¨+0");
    }
}
