package core.Accounts;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import core.Profile;
import core.Transaction;
import json.TransactionsPersistence;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class SavingsAccount extends AbstractAccount implements Serializable {
  public static final String file = "bankapp/core/src/main/java/json/TransactionsOverview.json";

  public SavingsAccount(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
    super(name, profile);
  }

  @Override
  public void transferTo(AbstractAccount account, int amount, String file) throws IOException {
    if (account == null) {
      throw new NullPointerException("Invalid account");
    }
    if (account.equals(this)) {
      throw new IllegalArgumentException("Cannot transfer to self");
    }
    if (account.getBalance() < amount) {
      throw new IllegalArgumentException("Account does not have enough money");
    }
    account.remove(amount);
    this.add(amount);
    TransactionsPersistence.writeTransactions(
        new Transaction(this.getProfile().getEmail(), account.getAccNr(), account.getProfile().getName(),
            this.getAccNr(), -amount),
        file);
    TransactionsPersistence.writeTransactions(
        new Transaction(account.getProfile().getEmail(), account.getAccNr(), this.getProfile().getName(),
            this.getAccNr(), amount),
        file);
    ;
  }

}
