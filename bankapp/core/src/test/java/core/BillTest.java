package core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import core.Accounts.AbstractAccount;
import core.Accounts.SpendingsAccount;

public class BillTest {
    private Profile profile1;
    private Profile profile2;
    private AbstractAccount acc1;
    private AbstractAccount acc2;

    private static final String currentDir = System.getProperty("user.dir");
    private final static String filename = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";

    @BeforeEach
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("jayan tayan", "jayantayan@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("klein ken", "kleinken@ntnu.no", "99997722", "idioteple6");
        acc1 = new SpendingsAccount("Payer", profile1);
        acc2 = new SpendingsAccount("Seller", profile2);
        profile1.addAccount(acc1);
        profile2.addAccount(acc2);
    }

    @Test
    @DisplayName("Testing the constructor")
    public void testConstructor() throws StreamReadException, DatabindException, IOException {
        Bill bill = new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0), (SpendingsAccount) profile1.getAccounts().get(0),
                profile1);
        assertNotNull(bill);
        assertThrows(IllegalArgumentException.class, () -> new Bill(-100, "Leie", "Sit", (SpendingsAccount) profile1.getAccounts().get(0),
                (SpendingsAccount) profile2.getAccounts().get(0), profile1));
        assertEquals(bill.getBillName(), "Leie");
        assertEquals(bill.getSellerName(), "Sit");
        assertEquals(bill.getAmount(), 100);
        assertEquals(bill.getProfile(), profile1);
        assertEquals(bill.getSellerAccount(), acc2);
        assertEquals(bill.getPayerAccount(), acc1);
        assertFalse(bill.isPaid());
    }

    @Test
    @DisplayName("Test paying")
    public void testPay() throws IOException {
        Bill bill = new Bill(100, "Leie", "Sit", (SpendingsAccount) profile2.getAccounts().get(0), (SpendingsAccount) profile1.getAccounts().get(0),
                profile1);
        SpendingsAccount payer = (SpendingsAccount) profile1.getAccounts().get(0);
        SpendingsAccount seller = (SpendingsAccount) profile2.getAccounts().get(0);
        profile1.addBill(bill);
        payer.add(100);
        bill.pay(filename);
        assertTrue(bill.isPaid());
        assertEquals(payer.getBalance(), 0);
        assertEquals(seller.getBalance(), 100);
    }
}
