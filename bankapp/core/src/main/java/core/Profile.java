package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import core.Accounts.AbstractAccount;
import core.Accounts.SpendingsAccount;

/**
 * Class that makes a profile
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Profile implements Serializable {
    private String name;
    private String email;
    private String tlf;
    private String password;
    private List<AbstractAccount> accounts = new ArrayList<>();
    private List<Bill> bills = new ArrayList<>();
    private ArrayList<String> landcodes = new ArrayList<>(Arrays.asList("ad", "ae", "af", "ag", "ai", "al", "am", "ao",
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
            "wf", "ws", "ye", "yt", "za", "zm", "zw", "com"));

    /**
     * Empty constructor used by the ProfileInformationManagement-class in order to
     * read from file
     */

    /**
     * Creates a new profile
     *
     * @param name     The profile name
     * @param email    The email connected to this profile
     * @param tlf      The telephone number connected to this profile
     * @param password The password to the profile
     * @throws IllegalArgumentException If the name, email, telephone number or
     *                                  password are not valid
     */
    public Profile(@JsonProperty("name") String name, @JsonProperty("email") String email,
            @JsonProperty("tlf") String tlf, @JsonProperty("password") String password) {
        if (!validName(name))
            throw new IllegalArgumentException("Invalid name");
        this.name = name;
        if (!validEmail(email))
            throw new IllegalArgumentException("Invalid email");
        if (!validPassword(password))
            throw new IllegalArgumentException("Invalid password");
        if (!validTlf(tlf))
            throw new IllegalArgumentException("Invalid phonenumber");

        this.email = email;
        this.tlf = tlf;
        this.password = password;
        System.out.println("Your profile was made successfully!");
    }

    /**
     * @param email
     *              Valid email is with format mailname@mail.landcode
     *              and the landcode must be a valid landcode (from the arraylist
     *              called landcodes)
     * @return whether an email is valid or not
     */
    private boolean validEmail(String email) {
        if (!email.contains("@")) {
            System.out.println("Your email must contain a @");
            return false;
        }
        if (!email.contains(".")) {
            System.out.println("Your email must contain a .");
            return false;
        }
        String[] splitAt = email.split("@");
        String[] splitDot = splitAt[1].split("\\.");

        if (landcodes.contains(splitDot[1])) {
            return true;
        }

        return false;
    }

    /**
     * @param password
     *                 A password is valid if it contains at least 8 characters,
     *                 where at least 1 of the characters are a number
     *                 and at least 1 of the characters are a letter
     * @return whether a password is valid or not
     */
    private boolean validPassword(String password) {
        int num = 0;
        int letter = 0;
        for (int i = 0; i < password.length(); i++) {
            if (isNumeric(String.valueOf(password.charAt(i))))
                num++;
            if (Character.isLetter(password.charAt(i)))
                letter++;
        }

        if (num == 0)
            System.out.println("The password must contain a number");
        if (letter == 0)
            System.out.println("The password must contain a letter");
        return (password.length() >= 8 && (num > 0) && (letter > 0));
    }

    /**
     * @param tlf
     *            valid tlf contains 8 numbers
     * @return whether a telephone number is valid or not
     */
    private boolean validTlf(String tlf) {
        return (tlf.length() == 8 && tlf.matches("[0-9]+"));
    }

    /**
     * @param name
     *             A name is valid if it contains of a surname and a lastname, and
     *             all of the characters are letters
     * @return whether a name is valid or not
     */
    private boolean validName(String name) {
        if (!name.contains(" ")) {
            System.out.println("Your name must contain your surname and lastname");
            return false;
        }
        String[] splits = name.split(" ");
        for (int i = 0; i < splits.length; i++) {
            for (int j = 0; j < splits[i].length(); j++) {
                if (!Character.isLetter(splits[i].strip().charAt(j))) {
                    System.out.println("Your name should only contain letters");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the text is numeric
     * 
     * @param test The text that's gonne be checked
     * @return The boolean value true if the text is numeric, and false if not
     */
    private static boolean isNumeric(String test) {
        try {
            Double.parseDouble(test);
            return true;
        }

        catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Add premade account to profile
     * 
     * @param account - The account to be added
     * @throws IllegalArgumentException - Throw exception if account already exists
     */
    public void addAccount(AbstractAccount account) {
        if (accounts.contains(account)) {
            throw new IllegalArgumentException("Account already exists");
        } else if (account.getProfile() != this) {
            throw new IllegalArgumentException("Account is connected to different profile");
        }
        accounts.add(account);
    }

    public void removeAccount(AbstractAccount account){
        for(AbstractAccount absAcc : getAccounts()){
            if(absAcc.getName().equals(account.getName())){
                accounts.remove(absAcc);
            }
        }
    }

    /**
     * Add a specific bill to this profiles list of bills
     * 
     * @param bill - bill to be paid
     */
    public void addBill(Bill bill) {
        if (bills.contains(bill)) {
            throw new IllegalArgumentException("Bill already exists");
        }
        if (bill.getProfile() != this) {
            throw new IllegalArgumentException("Bill does not belong to this profile");
        }
        bills.add(bill);
    }

    /**
     * Remove given bill from list if it has been paid
     * 
     * @param bill - bill to be removed
     */
    public void removeBill(Bill bill) {
        if (bill.isPaid() && bills.contains(bill)) {
            bills.remove(bill);
        } else {
            throw new IllegalArgumentException("cannot remove bill");
        }
    }

    @JsonIgnore
    public int getTotalBalance() {
        return accounts.stream().mapToInt(account -> account.getBalance()).sum();
    }

    /**
     * Show preview of total balance after paid bills
     * 
     */
    public int previewBalance() {
        int balance = 0;
        int billAmount = 0;
        for (Bill bill : bills) {
            billAmount += bill.getAmount();
        }
        for (AbstractAccount account : accounts) {
            if (account.showInPreview()) {
                balance += account.getBalance();
            }

        }
        return balance - billAmount;
    }

    /**
     * Changes the password if the new password is valid
     * 
     * @param password The new password
     */
    public void changePassword(String password) {
        if (!validPassword(password))
            throw new IllegalArgumentException("Not valid password");
        this.password = password;
    }

    /**
     * Changes the telephone number if number is valid
     * 
     * @param tlf The new telephone number
     */
    public void changeTlf(String tlf) {
        if (!validTlf(tlf))
            throw new IllegalArgumentException("Not valid telephone number");
        this.tlf = tlf;

    }

    /**
     * Changes the email if email is valid 
     * 
     * @param email The new email
     */
    public void changeEmail(String email){
        if(!validEmail(email))
            throw new IllegalArgumentException("Not valid email");
        this.email = email;
    }

    /**
     * Returns the email connected to this profile
     * 
     * @return The email connected to this profile
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the name connected to this account
     * 
     * @return The name connected to this account
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the phone number connected to this account
     * 
     * @return The phone number connected to this account
     */
    public String getTlf() {
        return tlf;
    }

    /**
     * Returns the account password
     * 
     * @return The account password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns list of all accounts
     * 
     * @return list of all accounts
     */
    public List<AbstractAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * Returns list of all bills
     * 
     * @return list of all bills
     */
    public List<Bill> getBills() {
        return new ArrayList<>(bills);
    }

    public boolean ownsAccount(AbstractAccount account){
        System.out.println(accounts);
        System.out.println(account);
        return accounts.stream().anyMatch(a -> a.getAccNr().equals(account.getAccNr()));
    }

    public static void main(String[] args) {
        Profile pro = new Profile("Nath Mul", "nath@gmail.com", "40897346", "yeyeyeyeye1");
        SpendingsAccount acc = new SpendingsAccount("nameAcc", pro);
        pro.addAccount(acc);
        SpendingsAccount acc2 = new SpendingsAccount("nameAcc2", pro);
        pro.addAccount(acc2);
        System.out.println(pro.getAccounts());
        pro.removeAccount(acc2);
        System.out.println(pro.getAccounts());
    }
}
