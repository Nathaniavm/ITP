package core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import core.accounts.AbstractAccount;
import core.accounts.SpendingsAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that makes a profile.
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Profile implements Serializable {
  private String name;
  private final String email;
  private String tlf;
  private String password;
  private List<AbstractAccount> accounts = new ArrayList<>();
  private ArrayList<String> landcodes = new ArrayList<>(Arrays.asList(
      "ad", "ae", "af", "ag", "ai", "al", "am", "ao",
      "aq", "ar", "as", "at", "au", "aw", "ax", "az",
      "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bl",
      "bm", "bn", "bo", "bq", "br", "bs", "bt", "bv", "bw",
      "by", "bz",
      "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm",
      "cn", "co", "cr", "cu", "cv", "cw", "cx", "cy", "cz",
      "de", "dj", "dk", "dm", "do", "dz",
      "ec", "ee", "eg", "eh", "er", "es", "et",
      "fi", "fj", "fk", "fm", "fo", "fr",
      "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm",
      "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy",
      "hk", "hm", "hn", "hr", "ht", "hu",
      "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it",
      "je", "jm", "jo", "jp",
      "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz",
      "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly",
      "ma", "mc", "md", "me", "mf", "mg", "mh", "mk", "ml", "mm", "mn",
      "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv",
      "mw", "mx", "my", "mz",
      "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz",
      "om",
      "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps",
      "pt", "pw", "py",
      "qa",
      "re", "ro", "rs", "ru", "rw",
      "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl",
      "sm", "sn", "so", "sr", "ss", "st", "sv", "sx",
      "sy", "sz",
      "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to",
      "tr", "tt", "tv", "tw", "tz",
      "ua", "ug", "um", "us", "uy", "uz",
      "va", "vc", "ve", "vg", "vi", "vn", "vu",
      "wf", "ws",
      "ye", "yt",
      "za", "zm", "zw",
      "com"));

  /**
   * Creates a new profile.
   *
   * @param name     The profile name
   * @param email    The email connected to this profile
   * @param tlf      The telephone number connected to this profile
   * @param password The password to the profile
   * 
   * @throws IllegalArgumentException If the name, email, telephone number or
   *                                  password are not valid
   * 
   */
  public Profile(@JsonProperty("name") String name, @JsonProperty("email") String email,
      @JsonProperty("tlf") String tlf, @JsonProperty("password") String password) {
    if (!validName(name)) {
      throw new IllegalArgumentException("Invalid name");
    }
    this.name = name;
    if (!validEmail(email)) {
      throw new IllegalArgumentException("Invalid email");
    }
    if (!validPassword(password)) {
      throw new IllegalArgumentException("Invalid password");
    }
    if (!validTlf(tlf)) {
      throw new IllegalArgumentException("Invalid phonenumber");
    }
    this.email = email;
    this.tlf = tlf;
    this.password = password;
    System.out.println("Your profile was made successfully!");
  }

  /**
   * Checks if an email is valid.
   *
   * @param email Valid email is with format mailname@mail.landcode
   *              and the landcode must be a valid landcode (from the arraylist
   *              called landcodes)
   *
   * @return Whether an email is valid or not
   * 
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
   * Checks if password is valid.
   *
   * @param password A password is valid if it contains at least 8 characters,
   *                 where at least 1 of the characters are a number
   *                 and at least 1 of the characters are a letter
   *
   * @return Whether a password is valid or not
   * 
   */
  private boolean validPassword(String password) {
    int num = 0;
    int letter = 0;
    for (int i = 0; i < password.length(); i++) {
      if (isNumeric(String.valueOf(password.charAt(i)))) {
        num++;
      }
      if (Character.isLetter(password.charAt(i))) {
        letter++;
      }
    }

    if (num == 0) {
      System.out.println("The password must contain a number");
    }
    if (letter == 0) {
      System.out.println("The password must contain a letter");
    }
    return (password.length() >= 8 && (num > 0) && (letter > 0));
  }

  /**
   * Checks if telephone number is valid.
   *
   * @param tlf Valid tlf contains 8 numbers
   *
   * @return Whether a telephone number is valid or not
   * 
   */
  private boolean validTlf(String tlf) {
    return (tlf.length() == 8 && tlf.matches("[0-9]+"));
  }

  /**
   * Checks if a name is valid.
   *
   * @param name A name is valid if it contains of a surname and a lastname, and
   *             all of the characters are letters
   *
   * @return Whether a name is valid or not
   * 
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
   * Checks if the text is numeric.
   *
   * @param test The text that's gonne be checked
   *
   * @return The boolean value true if the text is numeric, and false if not
   * 
   */
  private static boolean isNumeric(String test) {
    try {
      Double.parseDouble(test);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }

  /**
   * Add premade account to profile.
   *
   * @param account The account to be added
   *
   * @throws IllegalArgumentException Throw exception if account already exists
   * 
   */
  public void addAccount(AbstractAccount account) {
    if (accounts.contains(account)) {
      throw new IllegalArgumentException("Account already exists");
    } else if (account.getProfile() != this) {
      throw new IllegalArgumentException("Account is connected to different profile");
    }

    for (AbstractAccount absAcc : this.accounts) {
      if (account.getName().equals(absAcc.getName())) {
        throw new IllegalArgumentException("Another account has this name");
      }
    }
    accounts.add(account);
  }

  /**
   * Removes an account form this profile.
   *
   * @param account The account to be removen
   */
  public void removeAccount(AbstractAccount account) {
    for (AbstractAccount absAcc : getAccounts()) {
      if (absAcc.getName().equals(account.getName())) {
        accounts.remove(absAcc);
      }
    }
  }

  /**
   * Get the total balance among all accounts owned by this profile.
   *
   * @return The total balance among all accounts
   * 
   */

  @JsonIgnore
  public int getTotalBalance() {
    return accounts.stream().mapToInt(account -> account.getBalance()).sum();
  }


  /**
   * Changes the password if the new password is valid.
   *
   * @param password The new password
   * 
   */
  public void changePassword(String password) {
    if (!validPassword(password)) {
      throw new IllegalArgumentException("Not valid password");
    }
    this.password = password;
  }

  /**
   * Changes the telephone number if number is valid.
   *
   * @param tlf The new telephone number
   * 
   */
  public void changeTlf(String tlf) {
    if (!validTlf(tlf)) {
      throw new IllegalArgumentException("Not valid telephone number");
    }
    this.tlf = tlf;

  }

  /**
   * Returns the email connected to this profile.
   *
   * @return The email connected to this profile
   * 
   */
  public String getEmail() {
    return email;
  }

  /**
   * Returns the name connected to this account.
   *
   * @return The name connected to this account
   * 
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the phone number connected to this account.
   *
   * @return The phone number connected to this account
   * 
   */
  public String getTlf() {
    return tlf;
  }

  /**
   * Returns the account password.
   *
   * @return The account password
   * 
   */
  public String getPassword() {
    return password;
  }

  /**
   * Returns list of all accounts.
   *
   * @return List of all accounts
   * 
   */
  public List<AbstractAccount> getAccounts() {
    return new ArrayList<>(accounts);
  }

  /**
   * Checks whether this profile owns this account.
   *
   * @param account The account to check
   *
   * @return True if this profile owns the provided account. False otherwise
   *
   */
  public boolean ownsAccount(AbstractAccount account) {
    return accounts.stream().anyMatch(a -> a.getAccNr().equals(account.getAccNr()));
  }

  /**
   * Get all the spendingsaccount of the profile with bankcards.
   *
   * @return A list of this profile's bankcards
   * 
   */
  @JsonIgnore
  public List<BankCard> getBankCards() {
    return getAccounts().stream()
        .filter(account -> account instanceof SpendingsAccount)
        .map(account -> ((SpendingsAccount) account).getBankCard())
        .filter(card -> card != null)
        .collect(Collectors.toList());
  }

  /**
   * Gets a list of accounts withount bankcards.
   *
   * @return A list of spendingsaccounts without bankcards
   * 
   */
  @JsonIgnore
  public List<String> accountsWithoutBankcards() {
    List<String> lst = new ArrayList<>();
    if (getAccounts().size() != 0) {
      for (AbstractAccount absAcc : getAccounts()) {
        if (absAcc instanceof SpendingsAccount) {
          SpendingsAccount spendingsAccount = (SpendingsAccount) absAcc;
          if (!spendingsAccount.hasBankCard()) {
            lst.add(spendingsAccount.getAccNr());
          }
        }
      }
    }
    return new ArrayList<>(lst);
  }

  /**
   * Finds accounts without a blocked card, if they have a bankcard, and makes a
   * list containing those accounts' accountnumbers.
   *
   * @return A list containing the accountnumbers of the accounts without a
   *         blocked card
   *
   */
  @JsonIgnore
  public List<String> getListOfNotBlockedAccNrBankCards() {
    List<String> lst = new ArrayList<>();
    if (getAccounts().size() != 0) {
      for (AbstractAccount absAcc : getAccounts()) {
        if (absAcc instanceof SpendingsAccount) {
          SpendingsAccount spendingsAccount = (SpendingsAccount) absAcc;
          if (spendingsAccount.hasBankCard() && !spendingsAccount.getBankCard().isCardBlocked()) {
            lst.add(spendingsAccount.getAccNr());
          }
        }
      }
    }
    return new ArrayList<>(lst);
  }

  /**
   * Finds accounts with a blocked card, if they have a bankcard, and makes a list
   * containing those accounts' accountnumbers.
   *
   * @return A list containing the accountnumbers of the accounts with a blocked
   *         card
   * 
   */
  @JsonIgnore
  public List<String> getListOfBlockedAccNrBankCards() {
    List<String> lst = new ArrayList<>();
    if (getAccounts().size() != 0) {
      for (AbstractAccount absAcc : getAccounts()) {
        if (absAcc instanceof SpendingsAccount) {
          SpendingsAccount spendingsAccount = (SpendingsAccount) absAcc;
          if (spendingsAccount.hasBankCard() && spendingsAccount.getBankCard().isCardBlocked()) {
            lst.add(spendingsAccount.getAccNr());
          }
        }
      }
    }
    return new ArrayList<>(lst);
  }

  /**
   * Finds the bankcard of a given spendingsaccount's accountnumber.
   *
   * @param spendingsAccountNr An accountnumber of a spendingsaccount
   *
   * @return A bankcard
   * 
   */
  @JsonIgnore
  public BankCard getBankCard(String spendingsAccountNr) {
    if (getBankCards().isEmpty()) {
      throw new NullPointerException();
    }
    BankCard bankCard = null;
    bankCard = this.getBankCards().stream()
        .filter(bankCard2 -> bankCard2.getAccount().getAccNr().equals(spendingsAccountNr))
        .findFirst()
        .orElse(null);
    if (bankCard == null) {
      throw new IllegalArgumentException("Account does not have bankcard");
    }
    return bankCard;
  }

  /**
   * Finds a spendingsaccount with a given accountnumber.
   *
   * @param spendingsAccountNr The spendingsaccount's accountnumber to search for
   *
   * @return The spendingsaccount
   *
   * @throws IllegalArgumentException Throws if spendingsaccount does not exist
   */
  public SpendingsAccount findSpendingsAccount(String spendingsAccountNr) {
    AbstractAccount abstractAccount = null;
    abstractAccount = this.getAccounts()
        .stream()
        .filter(account -> account.getAccNr().equals(spendingsAccountNr) && account instanceof SpendingsAccount)
        .findFirst()
        .orElse(null);
    if (abstractAccount == null) {
      throw new IllegalArgumentException("There is no such spendingsaccount");
    }
    SpendingsAccount spendingsAccount = (SpendingsAccount) abstractAccount;
    return spendingsAccount;
  }


  /**
   * Used in transfer operation to find the
   * 
   * @param profile
   * @param accountNr
   * @return
   */
  public AbstractAccount findAbstractAccountByAccNr(String accountNr) {
    AbstractAccount acc1 = this.getAccounts().stream()
        .filter(account -> account.getAccNr().equals(accountNr))
        .findFirst()
        .orElse(null);
    return acc1;
  }

  public AbstractAccount findAbstractAccountByName(String name) {
    AbstractAccount acc1 = this.getAccounts().stream()
        .filter(account -> account.getName().equals(name))
        .findFirst()
        .orElse(null);
    return acc1;
  }

  public List<SpendingsAccount> getSpendingsAccounts(){
    return getAccounts().stream().filter(account -> (account instanceof SpendingsAccount)).map(account -> (SpendingsAccount) account).collect(Collectors.toList());
  }


}
