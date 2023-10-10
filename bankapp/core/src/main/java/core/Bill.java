package core;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Bill implements Serializable {

    private int amount;
    private String billName;
    private String sellerName;
    private Account sellerAccount;
    private Profile payer;
    private Account payerAccount;
    private boolean paid = false;

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
        payer.addBill(this);
    }

    public void pay() {
        sellerAccount.transferTo(payerAccount, amount);
        paid = true;
    }

    public boolean isPaid() {
        return paid;
    }

    public int getAmount() {
        return amount;
    }

    public String getBillName() {
        return billName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Account getSellerAccount() {
        return sellerAccount;
    }

    public Account getPayerAccount() {
        return payerAccount;
    }

    public Profile getProfile() {
        return payer;
    }
}
