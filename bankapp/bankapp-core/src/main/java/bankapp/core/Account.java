package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String profileName;
    private String name;
    private Balance balance;
    private static List<String> accNrs = new ArrayList<>();
    private String accNr;
    private BankCard bankCard;

    /**
     * Makes an account with given name and generates account number
     * 
     * @param name takes inn the name of the account
     */
    public Account(String name, String profileName) {
        balance = new Balance();
        this.profileName = profileName;
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
        bankCard = new BankCard(profileName);
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
    public void renameAccount(String name){
        this.name = name;
    }

    /**
     * 
     * @return corresponding bankcard object
     */
    public BankCard getBankCard() {
        return bankCard;
    }
    
}
