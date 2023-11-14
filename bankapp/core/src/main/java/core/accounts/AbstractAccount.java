package core.accounts;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import core.Balance;
import core.Profile;
import core.Transaction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A general class for the different account types. Implements methods common to
 * every account.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "accountType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = BsuAccount.class, name = "BSUAccount"),
    @JsonSubTypes.Type(value = SavingsAccount.class, name = "SavingsAccount"),
    @JsonSubTypes.Type(value = SpendingsAccount.class, name = "SpendingsAccount")
})
public abstract class AbstractAccount implements Serializable {
  private Profile profile;
  private String name;
  private Balance balance;
  private static List<String> accNrs = new ArrayList<>();
  private List<Transaction> transactions = new ArrayList<>();
  private String accNr;
  private boolean showInPreview = false;
  private static final Random RANDOM = new Random();

  /**
   * Create a new abstract account.
   *
   * @param name    The name of the account
   * @param profile The profile owning the account
   * 
   */
  public AbstractAccount(@JsonProperty("name") String name,
      @JsonProperty("profile") Profile profile) {
    balance = new Balance(0);
    this.profile = profile;
    this.name = name;
    setAccNr();
    while (accNrs.contains(accNr)) {
      setAccNr();
    }
    accNrs.add(accNr);
  }

  /**
   * Gets the name of the account.
   *
   * @return name of the account
   * 
   */
  public String getName() {
    return name;
  }

  /**
   * Increase balance by given amount.
   *
   * @param amount
   * 
   */
  public void add(int amount) {
    balance.increase(amount);
  }

  /**
   * Gets the balance of an account.
   *
   * @return Balance of the account
   * 
   */
  public int getBalance() {
    return balance.getBalance();
  }

  /**
   * Reduce balance by given amount.
   *
   * @param amount The amount to remove
   * 
   */
  public void remove(int amount) {
    balance.decrease(amount);
  }

  /**
   * Sets account number to random number.
   * 
   */
  public void setAccNr() {
    accNr = "1234 ";
    for (int i = 0; i < 7; i++) {
      if (accNr.length() == 7) {
        accNr += " ";
      }
      accNr += RANDOM.nextInt(10);
    }
  }

  /**
   * Gets the account's accountnumber.
   *
   * @return The account's accountnumber
   * 
   */
  public String getAccNr() {
    return accNr;
  }

  /**
   * Method for renaming an account.
   *
   * @param name To change the account name to
   * 
   */
  public void renameAccount(String name) {
    this.name = name;
  }

  /**
   * Gets the profile corresponding to this account.
   *
   * @return Corresponding profile object
   * 
   */

  public Profile getProfile() {
    return profile;
  }

  /**
   * Change whether you want this account to be counted when previewing remaining
   * balance.
   * 
   */
  public void changePreview() {
    showInPreview = !showInPreview;
  }

  /**
   * Return whether showInPreview is true or false.
   * 
   */
  public boolean showInPreview() {
    return showInPreview;
  }

  /**
   * Add a transaction to the account's transaction-list.
   *
   * @param transaction The transaction to be added
   * 
   */
  public void addTransaction(Transaction transaction) {
    if (!(transaction.getTransactionFrom().equals(getAccNr()))
        && !(transaction.getTransactionTo().equals(getAccNr()))) {
      throw new IllegalArgumentException(
          "Can not make a transaction between accounts that is not owned by this profile");
    }
    transactions.add(transaction);
  }

  /**
   * Gets the full list of transactions done by this account.
   *
   * @return A list of transactions
   * 
   */
  public List<Transaction> getTransaction() {
    return new ArrayList<>(transactions);
  }

  /**
   * Transfer money from given account to this account.
   *
   * @param account The account we are transferring from
   * @param amount  Amount to transfer
   * 
   */
  public void transferFrom(AbstractAccount account, int amount) {
    if (account == null) {
      throw new NullPointerException("Invalid account");
    }
    if (!(account.getProfile().equals(this.getProfile()))) {
      throw new IllegalArgumentException("Can not transfer between account you don't own");
    }
    if (account instanceof BsuAccount) {
      throw new IllegalArgumentException("You can't take money out of a BSU account");
    }
    if (account.equals(this)) {
      throw new IllegalArgumentException("Cannot transfer to self");
    }
    if (account.getBalance() < amount) {
      throw new IllegalArgumentException("Account does not have enough money");
    }
    account.remove(amount);
    this.add(amount);
    addTransaction(new Transaction(this.getProfile().getEmail(),this.getAccNr(),
     account.getProfile().getName(), account.getAccNr(), -amount,"(From transfer)"));
    account.addTransaction(
        new Transaction(account.getProfile().getEmail(), account.getAccNr(),
            this.getProfile().getName(), this.getAccNr(), amount,"(From transfer)"));
  }

}
