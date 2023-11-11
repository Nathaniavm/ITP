package core;

import java.io.Serializable;

/**
 * Class that represents a balance that can be increased or decreased.
 * 
 */
public class Balance implements Serializable {
  private int balance;

  public Balance(int amount) {
    this.balance = amount;
  }

  /**
   * Method for increasing money.
   *
   * @param amount To increase balance by
   * @throws IllegalArgumentException If amount less than 1
   * 
   */
  public void increase(int amount) {
    if (amount <= 0) {
      throw new IllegalArgumentException("Cannot increase by less than 1");
    }
    balance += amount;
  }

  /**
   * Method for decreasing money.
   *
   * @param amount To decrease balance by
   * @throws IllegalArgumentException If balance drops below 0 or amount is less
   *                                  than 1
   * 
   */
  public void decrease(int amount) {
    if (balance - amount < 0 || amount <= 0) {
      throw new IllegalArgumentException("Cannot remove amount");
    }
    balance -= amount;
  }

  /**
   * Gets the current balance of this account.
   *
   * @return The balance
   * 
   */
  public int getBalance() {
    return balance;
  }

}
