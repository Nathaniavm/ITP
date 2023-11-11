package core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import core.accounts.AbstractAccount;
import core.accounts.SpendingsAccount;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

public class BillTest {
    private Profile profile1;
    private Profile profile2;
    private AbstractAccount acc1;
    private AbstractAccount acc2;

    @BeforeEach
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("Lise Hansen", "lise@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("Thomas Hansen", "thomas@ntnu.no", "99997722", "idioteple6");
        acc1 = new SpendingsAccount("Payer", profile1);
        acc2 = new SpendingsAccount("Seller", profile2);
        profile1.addAccount(acc1);
        profile2.addAccount(acc2);
    }

    @Test
    @DisplayName("Testing the constructor. Should throw an IllegalArgumentException if billname, sellername, seller, payer or one of the accounts are null. Should also throw if selleraccount is payeraccount, or if payer owns selleraccount")
    public void testConstructor() {
        Bill bill = new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0),
                (SpendingsAccount) profile1.getAccounts().get(0),
                profile1);
        SpendingsAccount acc3 = new SpendingsAccount("Payer2", profile1);
        profile1.addAccount(acc3);
        assertNotNull(bill);
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(-100, "Leie", "Sit", (SpendingsAccount) profile1.getAccounts().get(0),
                        (SpendingsAccount) profile2.getAccounts().get(0), profile1));
        assertEquals(bill.getBillName(), "Leie");
        assertEquals(bill.getSellerName(), "Sit");
        assertEquals(bill.getAmount(), 100);
        assertEquals(bill.getProfile(), profile1);
        assertEquals(bill.getSellerAccount(), acc2);
        assertEquals(bill.getPayerAccount(), acc1);
        assertFalse(bill.isPaid());

        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, null, "Sit", (SpendingsAccount) profile2.getAccounts().get(0),
                        (SpendingsAccount) profile1.getAccounts().get(0),
                        profile1));
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", null, (SpendingsAccount) profile2.getAccounts().get(0),
                        (SpendingsAccount) profile1.getAccounts().get(0),
                        profile1));
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0),
                        (SpendingsAccount) profile1.getAccounts().get(0),
                        null));
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", "Sit", null,
                        (SpendingsAccount) profile1.getAccounts().get(0),
                        profile1));
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0),
                        null,
                        profile1));

        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", "Sit", (SpendingsAccount) profile1.getAccounts().get(0),
                        (SpendingsAccount) profile1.getAccounts().get(0),
                        profile1));
        assertThrows(IllegalArgumentException.class,
                () -> new Bill(100, "Leie", "Sit", (SpendingsAccount) profile1.getAccounts().get(0),
                        (SpendingsAccount) profile1.getAccounts().get(1),
                        profile1));
    }

    @Test
    @DisplayName("Test paying")
    public void testPay() throws IOException {
        Bill bill = new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0),
                (SpendingsAccount) profile1.getAccounts().get(0),
                profile1);
        SpendingsAccount payer = (SpendingsAccount) profile1.getAccounts().get(0);
        SpendingsAccount seller = (SpendingsAccount) profile2.getAccounts().get(0);
        profile1.addBill(bill);
        payer.add(100);
        bill.pay();
        assertTrue(bill.isPaid());
        assertEquals(payer.getBalance(), 0);
        assertEquals(seller.getBalance(), 100);
    }
}
