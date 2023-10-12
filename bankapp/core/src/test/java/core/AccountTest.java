package core;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class AccountTest {

    
    Profile profile = new Profile("Ola Nordmann", "ola@gmail.com", "12345678", "passord123");

    @Test
    @DisplayName("Tests if the constructor makes an account with name equal to the input")
    public void testConstructor() {
        Account newAcc = new Account("Acc1", profile);
        String name = newAcc.getName();
        assertEquals("Acc1", name);
    }

    @Test
    @DisplayName("tests if the transaction instruction works ")
    public void testBalance() {
        Account newAcc = new Account("Acc2", profile);
        newAcc.add(1500);
        newAcc.remove(500);
        assertEquals(1000, newAcc.getBalance());
    }

    @Test
    @DisplayName("tests transaction between two accounts, and if there will be an IllegalArgumentException when we try to tranfer more money than the balance in the account")
    public void testTransfer() {
        Account acc1 = new Account("acc1", profile);
        Account acc2 = new Account("acc2", profile);
        acc1.add(100);

        assertThrows(IllegalArgumentException.class, () -> acc2.transferTo(acc1, 150));
        assertTrue(acc1.getBalance() == 100);

        acc2.transferTo(acc1, 50);
        assertTrue(acc1.getBalance() == acc2.getBalance());
    }

    @Test
    @DisplayName("Tests if account number is generated correctly")
    public void testAccountNr() {
        Account acc1 = new Account("acc1", profile);
        Account acc2 = new Account("acc2", profile);
        Account acc3 = new Account("acc3", profile);
        Account acc4 = new Account("acc4", profile);

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
        Account acc1 = new Account("acc1", profile);
        assertEquals(acc1.getName(), "acc1");
        acc1.renameAccount("Savings account");
        assertEquals(acc1.getName(), "Savings account");
    }

    @Test
    @DisplayName("Test assigning of bankcard to account")
    public void testMakeBankCard() {
        Account acc1 = new Account("acc1", profile);
        assertNull(acc1.getBankCard());
        acc1.createBankCard();
        assertEquals(acc1.getBankCard().getCardholder(), "Ola Nordmann");
    }
}
