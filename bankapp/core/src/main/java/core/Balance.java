package core;

public class Balance {
    private int balance;

    public Balance(int amount) {
        this.balance = amount;
    }

    /**
     * 
     * @param amount - to increase balance by
     * @throws IllegalArgumentException - if amount less than 1
     */
    public void increase(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Cannot increase by less than 1");
        }
        balance += amount;
    }

    /**
     * 
     * @param amount - to decrease balance by
     * @throws IllegalArgumentException - if balance drops below 0 or amount is less
     *                                  than 1
     */
    public void decrease(int amount) {
        if (balance - amount < 0 || amount <= 0) {
            throw new IllegalArgumentException("Cannot remove amount");
        }
        balance -= amount;
    }

    /**
     * 
     * @return balance
     */
    public int getBalance() {
        return balance;
    }

}
