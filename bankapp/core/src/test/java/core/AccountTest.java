package core;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Accounts.SpendingsAccount;

public class AccountTest {

    private Profile profile = new Profile("Ola Nordmann", "ola@gmail.com", "12345678", "passord123");
    private static final String currentDir = System.getProperty("user.dir");
    private final static String filename = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";

    @Test
    @DisplayName("Tests if the constructor makes an account with name equal to the input")
    public void testConstructor() {
        SpendingsAccount newAcc = new SpendingsAccount("Acc1", profile);
        String name = newAcc.getName();
        assertEquals("Acc1", name);
    }

    @Test
    @DisplayName("tests if the transaction instruction works ")
    public void testBalance() {
        SpendingsAccount newAcc = new SpendingsAccount("Acc2", profile);
        newAcc.add(1500);
        newAcc.remove(500);
        assertEquals(1000, newAcc.getBalance());
    }

    @Test
    @DisplayName("tests transaction between two accounts, and if there will be an IllegalArgumentException when we try to tranfer more money than the balance in the account")
    public void testTransfer() throws IOException {
        SpendingsAccount acc1 = new SpendingsAccount("acc1", profile);
        SpendingsAccount acc2 = new SpendingsAccount("acc2", profile);
        acc1.add(100);

        assertThrows(IllegalArgumentException.class, () -> acc2.transferTo(acc1, 150, filename));
        assertTrue(acc1.getBalance() == 100);

        acc2.transferTo(acc1, 50, filename);
        assertTrue(acc1.getBalance() == acc2.getBalance());
    }

    @Test
    @DisplayName("Tests if account number is generated correctly")
    public void testAccountNr() {
        SpendingsAccount acc1 = new SpendingsAccount("acc1", profile);
        SpendingsAccount acc2 = new SpendingsAccount("acc2", profile);
        SpendingsAccount acc3 = new SpendingsAccount("acc3", profile);
        SpendingsAccount acc4 = new SpendingsAccount("acc4", profile);

        assertFalse(acc1.getAccNr() == acc2.getAccNr());
        assertFalse(acc1.getAccNr() == acc3.getAccNr());
        assertFalse(acc1.getAccNr() == acc4.getAccNr());

        assertFalse(acc2.getAccNr() == acc3.getAccNr());
        assertFalse(acc2.getAccNr() == acc4.getAccNr());

        assertFalse(acc3.getAccNr() == acc4.getAccNr());

        assertEquals(acc1.getAccNr().length(), 13);
    }

    @Test
    @DisplayName("Test name change method")
    public void testAccountName() {
        SpendingsAccount acc1 = new SpendingsAccount("acc1", profile);
        assertEquals(acc1.getName(), "acc1");
        acc1.renameAccount("Savings account");
        assertEquals(acc1.getName(), "Savings account");
    }

    @Test
    @DisplayName("Test assigning of bankcard to account")
    public void testMakeBankCard() {
        SpendingsAccount acc1 = new SpendingsAccount("acc1", profile);
        assertNull(acc1.getBankCard());
        acc1.createBankCard();
        assertEquals(acc1.getBankCard().getCardholder(), "Ola Nordmann");
    }
}
