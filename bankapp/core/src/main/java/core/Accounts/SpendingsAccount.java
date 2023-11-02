package core.Accounts;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import core.BankCard;
import core.Profile;
import core.Transaction;

/*
 * Class that makes an account
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class SpendingsAccount extends AbstractAccount implements Serializable {
  private BankCard bankCard;

  /**
   * Makes an account with given name and generates account number
   * 
   * @param name takes inn the name of the account
   */
  public SpendingsAccount(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
    super(name, profile);
  }

  /**
   * Creates bankcard for the account
   */
  public void createBankCard() {
    bankCard = new BankCard(this.getProfile().getName(), this);
  }

  /**
   * 
   * @return corresponding bankcard object
   */
  public BankCard getBankCard() {
    return bankCard;
  }

  public void pay(SpendingsAccount account, int amount) {
    if ((account.getProfile().equals(this.getProfile()))) {
      throw new IllegalArgumentException("Can not pay yourself");
    }
    if (this.getBalance() < amount) {
      throw new IllegalArgumentException("Account does not have enough money");
    }
    this.remove(amount);
    account.add(amount);
    addTransaction(new Transaction(this.getProfile().getEmail(), account.getAccNr(), account.getProfile().getName(),
        this.getAccNr(), -amount));
    account.addTransaction(
        new Transaction(account.getProfile().getEmail(), account.getAccNr(), this.getProfile().getName(),
            this.getAccNr(), amount));

  }

}
