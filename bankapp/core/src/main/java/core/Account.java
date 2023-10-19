package core;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import json.Transactions;

/*
 * Class that makes an account
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Account implements Serializable {
    private Profile profile;
    private String name;
    private Balance balance;
    private static List<String> accNrs = new ArrayList<>();
    private String accNr;
    private BankCard bankCard;
    private boolean showInPreview = false;
    private static final Random RANDOM = new Random();

    // private static final String file = currentDir +
    // "src/main/java/json/TransactionsOverview.json";

    public static final String file = "bankapp/core/src/main/java/json/TransactionsOverview.json";

    /**
     * Makes an account with given name and generates account number
     * 
     * @param name takes inn the name of the account
     */
    public Account(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
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
     * 
     * @return name of the account
     */
    public String getName() {
        return name;
    }

    /**
     * increase balance by given amount
     * 
     * @param amount
     * 
     */
    public void add(int amount) {
        balance.increase(amount);
    }

    /**
     * Transfer money from given account to this account
     * 
     * @param account - the account we are transferring from
     * @param amount  - amount to transfer
     * @throws IOException
     */
    public void transferTo(Account account, int amount, String file) throws IOException {
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
    }

    /**
     * 
     * @return balance of the account
     */
    public int getBalance() {
        return balance.getBalance();
    }

    /**
     * reduce balance by given amount
     * 
     * @param amount
     */
    public void remove(int amount) {
        balance.decrease(amount);
    }

    /**
     * sets account number to random number
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
     * Creates bankcard for the account
     */
    public void createBankCard() {
        bankCard = new BankCard(profile.getName(), this);
    }

    /**
     * 
     * @return accountnumber
     */
    public String getAccNr() {
        return accNr;
    }

    /**
     * 
     * @param name to change the account name to
     */
    public void renameAccount(String name) {
        this.name = name;
    }

    /**
     * 
     * @return corresponding bankcard object
     */
    public BankCard getBankCard() {
        return bankCard;
    }

    /**
     * 
     * @return corresponding profile object
     */

    public Profile getProfile() {
        return profile;
    }

    /**
     * change whether you want this account to be counted when previewing remaining
     * balance
     */
    public void changePreview() {
        showInPreview = !showInPreview;
    }

    /**
     * return whether showInPreview is true or false
     */
    public boolean showInPreview() {
        return showInPreview;
    }

    public static void main(String[] args) throws IOException {
        Profile profile1 = new Profile("Alice Zheng", "Alice@gmail.com", "15678902", "Passord456");
        Account account1 = new Account("Spending", profile1);
        profile1.addAccount(account1);

        Profile profile2 = new Profile("Philip Lam", "Philip@gmail.com", "15678906", "Passord123");
        Account account2 = new Account("Spending", profile2);
        profile2.addAccount(account2);

        account1.add(1000);
        account2.transferTo(account1, 500, file);

        List<Transactions> transactions = Transactions.readTransactions(file);
        System.out.println(transactions);
    }

}
