package core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Accounts.SpendingsAccount;

public class SpendingsAccountTest {

    private Profile profile;
    private SpendingsAccount spendingsAcc;

    @BeforeEach
    public void setUp() {
        profile = new Profile("Ola Nordmann", "ola@gmail.com", "12345678", "passord123");
        spendingsAcc = new SpendingsAccount("Main Spendingsaccount", profile);
    }

    @Test
    @DisplayName("Tests if the constructor makes an account with name equal to the input")
    public void testConstructor() {
        String name = spendingsAcc.getName();
        assertEquals("Main Spendingsaccount", name);
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
    @DisplayName("Tests if the transaction instruction works ")
    public void testBalance() {
        spendingsAcc.add(1500);
        spendingsAcc.remove(500);
        assertEquals(1000, spendingsAcc.getBalance());
    }

    @Test
    @DisplayName("Tests transaction between two accounts, and if there will be an IllegalArgumentException when we try to tranfer more money than the balance in the account")
    public void testTransfer() {
        SpendingsAccount acc2 = new SpendingsAccount("acc2", profile);
        spendingsAcc.add(100);

        assertThrows(IllegalArgumentException.class, () -> acc2.transferFrom(spendingsAcc, 150));
        assertTrue(spendingsAcc.getBalance() == 100);

        acc2.transferFrom(spendingsAcc, 50);
        assertTrue(spendingsAcc.getBalance() == acc2.getBalance());
    }

    @Test
    @DisplayName("Test name change method")
    public void testAccountName() {
        assertEquals(spendingsAcc.getName(), "Main Spendingsaccount");
        spendingsAcc.renameAccount("Spendingsaccount");
        assertEquals(spendingsAcc.getName(), "Spendingsaccount");
    }

    @Test
    @DisplayName("Test assigning of bankcard to account")
    public void testMakeBankCard() {
        assertNull(spendingsAcc.getBankCard());
        spendingsAcc.createBankCard();
        assertEquals(spendingsAcc.getBankCard().getCardholder(), "Ola Nordmann");
    }

    @Test
    @DisplayName("Test paying from an account. Should throw an IllegalArgumentException if tried to pay yourself, or if account don't have enough money")
    public void testPay() {
        spendingsAcc.add(1500);
        SpendingsAccount sAcc2 = new SpendingsAccount("Acc2", profile);
        Profile profile2 = new Profile("Kari Nordmann", "kari@gmail.com", "12345690", "passord123");
        SpendingsAccount sAcc3 = new SpendingsAccount("Acc3", profile2);

        spendingsAcc.pay(sAcc3, 100);
        assertTrue(sAcc3.getBalance() == 100);

        assertThrows(IllegalArgumentException.class, () -> spendingsAcc.pay(sAcc3, 20000));
        assertThrows(IllegalArgumentException.class, () -> spendingsAcc.pay(sAcc2, 100));

    }

    @Test
    @DisplayName("Test change preview")
    public void testChangePreview() {
        spendingsAcc.changePreview();
        assertTrue(spendingsAcc.showInPreview());
    }
}
