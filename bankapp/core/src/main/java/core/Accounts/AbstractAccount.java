package core.Accounts;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import core.Balance;
import core.Profile;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "accountType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BSUAccount.class, name = "BSUAccount"),
        @JsonSubTypes.Type(value = SavingsAccount.class, name = "SavingsAccount"),
        @JsonSubTypes.Type(value = SpendingsAccount.class, name = "SpendingsAccount")
})  
public abstract class AbstractAccount implements Serializable {
    private Profile profile;
    private String name;
    private Balance balance;
    private static List<String> accNrs = new ArrayList<>();
    private String accNr;
    private boolean showInPreview = false;
    private static final Random RANDOM = new Random();

    public AbstractAccount(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
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

    public abstract void transferTo(AbstractAccount account, int amount, String file) throws IOException;
}
