package core;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import core.Accounts.AbstractAccount;
import core.Accounts.SpendingsAccount;

/*
 * Class that creates a new bill that can be paid by a Profile. It contains informations about the bill amount, bill name, seller name, seller's account, payer's account, adn whether the bill has been paid or not
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Bill implements Serializable {

  private int amount;
  private String billName;
  private String sellerName;
  private AbstractAccount sellerAccount;
  private Profile payer;
  private AbstractAccount payerAccount;
  private boolean paid = false;

  public static final String filename = "bankapp/core/src/main/java/json/TransactionsOverview.json";

  /**
   * Makes a new bill object with specified properties
   * 
   * @param amount        The amount of the bill
   * @param billName      The name of the bill
   * @param sellerName    The name of the seller
   * @param sellerAccount The seller's account
   * @param payerAccount  The payer's account
   * @param payer         The payer's profile
   * @throws IOException
   * @throws DatabindException
   * @throws StreamReadException
   * @throws IllegalArgumentException If the amount is less han er equal to zero
   */
  public Bill(@JsonProperty("amount") int amount,
      @JsonProperty("billName") String billName,
      @JsonProperty("sellerName") String sellerName,
      @JsonProperty("sellerAccount") AbstractAccount sellerAccount,
      @JsonProperty("payerAccount") AbstractAccount payerAccount,
      @JsonProperty("profile") Profile payer) throws StreamReadException, DatabindException, IOException {

    if (amount == 0 || billName == null || sellerName == null || sellerAccount == null || payerAccount == null
        || payer == null) {
      throw new IllegalArgumentException("All of the fields should be filled");
    }

    if (amount < 0) {
      throw new IllegalArgumentException("Amount can not be negative");
    }
    if (sellerAccount.getAccNr().equals(payerAccount.getAccNr())) {
      throw new IllegalArgumentException("Seller and payer cannot be the same account");
    }
    if (payer.ownsAccount(sellerAccount)) {
      throw new IllegalArgumentException("Selleraccount should not be an account of payer");
    }

    this.amount = amount;
    this.billName = billName;
    this.sellerName = sellerName;
    this.sellerAccount = sellerAccount;
    this.payerAccount = payerAccount;
    this.payer = payer;
  }

  /**
   * Pays the bill by transfering the amount from the seller's account to the
   * payer's account
   * 
   * @throws IOException
   */
  public void pay(String filename) throws IOException {
    sellerAccount.transferTo(payerAccount, amount, filename);
    paid = true;
    payer.removeBill(this);
  }

  /**
   * Checks if the bill has been paid'
   * 
   * @return true if the bill has been paid; otherwise, false
   */
  public boolean isPaid() {
    return paid;
  }

  /**
   * Gets the amount of the bill
   * 
   * @return The bill amount
   */
  public int getAmount() {
    return amount;
  }

  /**
   * Gets the name of the bill
   * 
   * @return The bill name
   */
  public String getBillName() {
    return billName;
  }

  /**
   * Gets the name of the selller
   * 
   * @return The seller's name
   */
  public String getSellerName() {
    return sellerName;
  }

  /**
   * Gets the account of the selller
   * 
   * @return The seller's account
   */
  public AbstractAccount getSellerAccount() {
    return sellerAccount;
  }

  /**
   * Gets the account of the payer
   * 
   * @return The payer's account
   */
  public AbstractAccount getPayerAccount() {
    return payerAccount;
  }

  /**
   * Gets the profile of the payer
   * 
   * @return The payer's profile
   */
  public Profile getProfile() {
    return payer;
  }

  public static void main(String[] args) throws StreamReadException, DatabindException, IOException {
    Profile profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
    Profile profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
    SpendingsAccount acc1 = new SpendingsAccount("James", profile1);
    profile1.addAccount(acc1);
    SpendingsAccount acc2 = new SpendingsAccount("Heui", profile2);
    profile2.addAccount(acc2);
    Bill bill1 = new Bill(150, "Groceries", "Kari Nordmann", acc2, acc1, profile1);
    profile1.addBill(bill1);

  }

}
