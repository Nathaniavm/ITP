package core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
 * Class that creates a new bill that can be paid by a Profile. It contains informations about the bill amount, bill name, seller name, seller's account, payer's account, adn whether the bill has been paid or not
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Bill implements Serializable {

    private int amount;
    private String billName;
    private String sellerName;
    private Account sellerAccount;
    private Profile payer;
    private Account payerAccount;
    private boolean paid = false;

    /**
     * Makes a new bill object with specified properties
     * 
     * @param amount        The amount of the bill
     * @param billName      The name of the bill
     * @param sellerName    The name of the seller
     * @param sellerAccount The seller's account
     * @param payerAccount  The payer's account
     * @param payer         The payer's profile
     * @throws IllegalArgumentException If the amount is less han er equal to zero
     */
    public Bill(@JsonProperty("amount") int amount,
            @JsonProperty("billName") String billName,
            @JsonProperty("sellerName") String sellerName,
            @JsonProperty("sellerAccount") Account sellerAccount,
            @JsonProperty("payerAccount") Account payerAccount,
            @JsonProperty("profile") Profile payer) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can not be less than 1");
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
     */
    public void pay() {
        sellerAccount.transferTo(payerAccount, amount);
        paid = true;
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
    public Account getSellerAccount() {
        return sellerAccount;
    }

    /**
     * Gets the account of the payer
     * 
     * @return The payer's account
     */
    public Account getPayerAccount() {
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
}
