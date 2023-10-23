package core.Accounts;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import core.Balance;
import core.BankCard;
import core.Profile;
import json.Transactions;

/*
 * Class that makes an account
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class SpendingsAccount extends AbstractAccount implements Serializable {
    private BankCard bankCard;

    public static final String file = "bankapp/core/src/main/java/json/TransactionsOverview.json";

    /**
     * Makes an account with given name and generates account number
     * 
     * @param name takes inn the name of the account
     */
    public SpendingsAccount(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
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
     * Transfer money from given account to this account
     * 
     * @param account - the account we are transferring from
     * @param amount  - amount to transfer
     * @throws IOException
     */
    @Override
    public void transferTo(AbstractAccount account, int amount, String file) throws IOException {
        if (account.equals(this)) {
            throw new IllegalArgumentException("Cannot transfer to self");
        }
        if (account.equals(null)) {
            throw new NullPointerException("Invalid account");
        }
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Account does not have enough money");
        }
        account.remove(amount);
        this.add(amount);

        Transactions.writeTransactions(new Transactions(this.getProfile(), account, amount), file);
        Transactions.writeTransactions(new Transactions(account.getProfile(), this, amount), file);
        ;
    }

    /**
     * Creates bankcard for the account
     */
    public void createBankCard() {
        bankCard = new BankCard(profile.getName(), this);
    }

    /**
     * 
     * @return corresponding bankcard object
     */
    public BankCard getBankCard() {
        return bankCard;
    }

    public static void main(String[] args) throws IOException {
        Profile profile1 = new Profile("Alice Zheng", "Alice@gmail.com", "15678902", "Passord456");
        SpendingsAccount account1 = new SpendingsAccount("Spending", profile1);
        profile1.addAccount(account1);

        Profile profile2 = new Profile("Philip Lam", "Philip@gmail.com", "15678906", "Passord123");
        SpendingsAccount account2 = new SpendingsAccount("Spending", profile2);
        profile2.addAccount(account2);

        account1.add(1000);
        account2.transferTo(account1, 500, file);

        List<Transactions> transactions = Transactions.readTransactions(file);
        System.out.println(transactions);
    }

}
