package core.accounts;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import core.BankCard;
import core.Profile;
import core.Transaction;
import java.io.Serializable;

/**
 * Creates a new spendingsaccount to the given profile, with a given name.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class SpendingsAccount extends AbstractAccount implements Serializable {
  private BankCard bankCard;

  /**
   * Creates a new spendingsaccount.
   *
   * @param name    The name of the account
   * @param profile The profile to make an account for
   * 
   */
  public SpendingsAccount(@JsonProperty("name") String name,
      @JsonProperty("profile") Profile profile) {
    super(name, profile);
  }

  /**
   * Creates bankcard for the account.
   * 
   */
  public void createBankCard() {
    bankCard = new BankCard(this.getProfile().getName(), this);
    this.getProfile().addBankCard(bankCard);
  }

  public boolean hasBankCard() {
    return bankCard != null;
  }

  /**
   * Gets the account's bankcard.
   *
   * @return corresponding bankcard object
   * 
   */
  public BankCard getBankCard() {
    return bankCard;
  }

  /**
   * Method for paying to someone. The account paid to must be an account that you
   * don't own yourself.
   *
   * @param account The account paid to
   * @param amount  The amount paid
   * @throws IllegalArgumentException throws if you try to pay yourself, or if the
   *                                  account trying to pay doesn't have enough
   *                                  money
   * 
   */
  public void pay(SpendingsAccount account, int amount) {
    if ((account.getProfile().equals(this.getProfile()))) {
      throw new IllegalArgumentException("Can not pay yourself");
    }
    if (this.getBalance() < amount) {
      throw new IllegalArgumentException("Account does not have enough money");
    }
    this.remove(amount);
    account.add(amount);
    addTransaction(new Transaction(this.getProfile().getEmail(),
        account.getAccNr(), account.getProfile().getName(), this.getAccNr(), -amount));
    account.addTransaction(
        new Transaction(account.getProfile().getEmail(), this.getAccNr(),
            this.getProfile().getName(), account.getAccNr(), amount));

  }

}
