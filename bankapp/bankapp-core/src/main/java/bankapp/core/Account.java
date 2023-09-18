package bankapp.core;

public class Account {
    private String name;
    private Balance balance;
    private int accNr;
    private BankCard bankCard;


    public Account(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void add(int amount) {
        balance.increase(amount);
    }

    public void transferTo(Account account, int amount) {
        if (account.getBalance() < amount){
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
    public void setAccNr(int accNr) {
        
    }
}
