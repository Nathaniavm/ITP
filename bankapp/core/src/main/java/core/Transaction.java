package core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;

/**
 * Class that creates a transaction based on the "transferTo"-method in the
 * AbstractAccount-classes. It contains information about the email and
 * accountnumber of the
 * transferer, the accountnumber of the reciever and the amount. It also
 * contains information about name, depending on if the transaction describes a
 * transfer or a receive
 */

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Transaction implements Serializable {

  private String email;
  private String transactionTo;
  private String name;
  private String transactionFrom;
  private int amount;

  /**
   * Makes a new Transaction object with the specified properties
   *
   * @param email           The transferer's email
   * @param transactionTo   The accountnumber to the account that receives money
   * @param name            The name of the person owning the account that
   *                        receives money (if transaction describes the
   *                        transferer), or the name of the person sending
   *                        money (if transaction describes the receiver)
   * @param transactionFrom The accountnumber to the account that transfers money
   * @param amount          The amount transfered
   * 
   * @throws IllegalArgumentException Throws if you try to make a transaction
   *                                  between an account, to the same account
   * 
   */
  public Transaction(@JsonProperty("email") String email,
      @JsonProperty("transactionTo") String transactionTo,
      @JsonProperty("name") String name,
      @JsonProperty("transactionFrom") String transactionFrom, @JsonProperty("amount") int amount) {
    if (transactionFrom.equals(transactionTo)) {
      throw new IllegalArgumentException("Can not make a transaction from one account to the same account");
    }

    this.email = email;
    this.transactionTo = transactionTo;
    this.name = name;
    this.transactionFrom = transactionFrom;
    this.amount = amount;
  }

  /**
   * Getter for the email
   * 
   * @return The transferer's email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Getter for transactionTo
   * 
   * @return The accountnumber to the account that recieves money
   */
  public String getTransactionTo() {
    return transactionTo;
  }

  /**
   * Getter for name
   * 
   * @return The name of the person owning the account that recieves money, or
   *         sending the money
   */
  public String getName() {
    return name;
  }

  /**
   * Getter for transactionFrom
   * 
   * @return The accountnumber to the account that transfers money
   */
  public String getTransactionFrom() {
    return transactionFrom;
  }

  /**
   * Getter for the amount
   * 
   * @return The amount transfered
   */
  public int getAmount() {
    return amount;
  }

}
