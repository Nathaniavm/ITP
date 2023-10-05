package core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.UUIDGenerator.class, property="@id")
public class Account implements Serializable{
    private Profile profile;
    private String name;
    private Balance balance;
    private static List<String> accNrs = new ArrayList<>();
    private String accNr;
    private BankCard bankCard;
    private boolean showInPreview = false;

    /**
     * Makes an account with given name and generates account number
     * 
     * @param name takes inn the name of the account
     */
    public Account(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
        balance = new Balance(0);
        this.profile = profile;
        if (!profile.getAccounts().contains(this)){
            profile.addAccount(this);
        }
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
     */
    public void add(int amount) {
        balance.increase(amount);
    }

    /**
     * Transfer money from given account to this account
     * 
     * @param account - the account we are transferring from
     * @param amount  - amount to transfer
     */
    public void transferTo(Account account, int amount) {
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Account does not have enough money");
        }
        account.remove(amount);
        this.add(amount);
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
            Integer rand = (int) (Math.random() * 10);
            accNr += rand;
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

}
