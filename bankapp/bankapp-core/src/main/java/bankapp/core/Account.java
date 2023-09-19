package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private String name;
    private Balance balance;
    private static List<String> accNrs = new ArrayList<>();
    private String accNr;
    private BankCard bankCard;

    public Account(String name) {
        this.name = name;
        setAccNr();
        while (accNrs.contains(accNr)){
            setAccNr();
        }
        accNrs.add(accNr);
        balance = new Balance();
    }

    public String getName() {
        return name;
    }

    public void add(int amount) {
        balance.increase(amount);
    }

    public void transferTo(Account account, int amount) {
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Account does not have enough money");
        }
        account.remove(amount);
        this.add(amount);
    }

    public int getBalance() {
        return balance.getBalance();
    }

    public void remove(int amount) {
        balance.decrease(amount);
    }

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

    public String getAccNr() {
        return accNr;
    }
}
