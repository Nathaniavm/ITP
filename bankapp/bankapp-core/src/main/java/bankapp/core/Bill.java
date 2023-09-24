package bankapp.core;

public class Bill {

    private int amount;
    private String billName;
    private String sellerName;

    public Bill(int amount, String billName, String sellerName) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount can not be less than 1");
        }
        this.amount = amount;
        this.billName = billName;
        this.sellerName = sellerName;
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

}
