package bankapp.core;

public class Bill {

    private int amount;
    private String billName;
    private String sellerName;
    private Account sellerAccount;
    private Profile payer;
    private Account payerAccount;
    private boolean paid = false;

    public Bill(int amount, String billName, String sellerName, Account sellerAccount, Account payerAccount, Profile payer) {
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

    public void pay(){
        sellerAccount.transferTo(payerAccount, amount);
        paid = true;
    }
    public boolean isPaid(){
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

    public String getProfileName(){
        return payer.getName();
    }
}
