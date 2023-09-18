package bankapp.core;

public class Balance {
    private int balance = 0;
    
    public void increase(int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Cannot increase by less than 1");
        }
        balance += amount;
    }
    
    public void decrease(int amount){
        if (balance - amount < 0 || amount <= 0){
            throw new IllegalArgumentException("Cannot remove amount");
        }
        balance -= amount;
    }
    
    public int getBalance() {
        return balance;
    }
}
