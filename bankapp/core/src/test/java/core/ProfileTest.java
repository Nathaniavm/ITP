package core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;

import core.Accounts.SavingsAccount;
import core.Accounts.SpendingsAccount;

public class ProfileTest {
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    @BeforeEach
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("Petter Pan", "peter@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("Charles Darwin", "charles@ntnu.no", "99997722", "JegElskerITP123");
        profile3 = new Profile("Muhammed Ali", "ali@ntnu.no", "92457233", "loneyku9");
    }

    @Test
    @DisplayName("Testing the constructor")
    public void testConstructor() {
        assertNotNull(profile1);
        assertNotNull(profile2);
        assertNotNull(profile3);
    }

    @Test
    @DisplayName("Testing if the name is set correctly")
    public void testSetName() {
        assertEquals("Petter Pan", profile1.getName());
        assertEquals("Charles Darwin", profile2.getName());
        assertEquals("Muhammed Ali", profile3.getName());
        assertFalse(profile1.getName().equals(profile2.getName()));
        assertTrue(profile2.getName().equals("Charles Darwin"));

        assertTrue(profile1.getName().split(" ").length >= 2);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("a2 loke", "a2loke@example.no", "12345678", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("lisa", "lisa@example.no", "12345678", "generiskPassord1"));

    }

    @Test
    @DisplayName("Testing if the email is set correct")
    public void testSetEmail() {
        assertEquals("peter@ntnu.no", profile1.getEmail());
        assertEquals("ali@ntnu.no", profile3.getEmail());
        assertFalse(profile2.getEmail().equals(profile3.getEmail()));
        assertTrue(profile1.getEmail().equals("peter@ntnu.no"));

        assertTrue(profile1.getEmail().split("@").length == 2);
        assertTrue(profile3.getEmail().split("\\.").length == 2);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemonsntnu.no", "12345678", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("charlie geir", "charliegeir@ntnuno", "12345678", "generiskPassord1"));

    }

    @Test
    @DisplayName("Testing if the telefone number is set correct")
    public void testSetTlf() {
        assertEquals("98765432", profile1.getTlf());
        assertEquals("99997722", profile2.getTlf());
        assertEquals("92457233", profile3.getTlf());

        profile3.changeTlf("98658722");
        assertEquals("98658722", profile3.getTlf());

        assertTrue(profile1.getTlf().length() == 8);
        assertTrue(profile2.getTlf().length() == 8);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "123456a8", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12348", "generiskPassord1"));
        assertThrows(IllegalArgumentException.class, () -> profile1.changeTlf("2323234f"));
    }

    @Test
    @DisplayName("Testing if the password is set correct")
    public void testSetPassword() {
        assertEquals("passord111", profile1.getPassword());
        assertEquals("JegElskerITP123", profile2.getPassword());
        assertEquals("loneyku9", profile3.getPassword());

        profile2.changePassword("orangutang4");
        assertEquals("orangutang4", profile2.getPassword());

        assertTrue(profile2.getEmail().length() > 8);
        assertTrue(profile3.getEmail().length() > 8);

        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12345678", "generiskPassord"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12345678", "generisk"));
        assertThrows(IllegalArgumentException.class,
                () -> new Profile("millie mons", "milliemons@ntnu.no", "12345678", "12345"));
        assertThrows(IllegalArgumentException.class, () -> profile1.changePassword("baba123"));

    }

    @Test
    @DisplayName("Testing if an account gets created correctly")
    public void testCreateAccount() {
        assertEquals(0, profile1.getAccounts().size());
        SpendingsAccount acc = new SpendingsAccount("Savings", profile1);
        profile1.addAccount(acc);
        assertEquals(1, profile1.getAccounts().size());
        assertEquals("Savings", acc.getName());
    }

    @Test
    @DisplayName("Test adding of bills to profile. Should throw if profile does not own the bill, or if bill is already added")
    public void testAddBill() throws StreamReadException, DatabindException, IOException {
        SpendingsAccount acc1 = new SpendingsAccount("Spending", profile1);
        profile1.addAccount(acc1);
        profile1.getAccounts().get(0).add(1000);
        SpendingsAccount acc2 = new SpendingsAccount("NTNU", profile2);

        Bill bill = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile1);
        profile1.addBill(bill);
        assertTrue(profile1.getBills().contains(bill));
        assertThrows(IllegalArgumentException.class, () -> profile1.addBill(bill));

        Bill bill2 = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile2);
        assertThrows(IllegalArgumentException.class, () -> profile1.addBill(bill2));
    }

    @Test
    @DisplayName("Test removal of bills from profile")
    public void testRemoveBill() throws IOException {
        SpendingsAccount acc1 = new SpendingsAccount("Spending", profile1);
        profile1.addAccount(acc1);
        profile1.getAccounts().get(0).add(1000);
        SpendingsAccount acc2 = new SpendingsAccount("NTNU", profile2);
        profile2.addAccount(acc2);
        Bill bill = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile1);
        profile1.addBill(bill);
        assertThrows(IllegalArgumentException.class, () -> profile1.removeBill(bill));
        bill.pay();
        assertFalse(profile1.getBills().contains(bill));
        assertThrows(IllegalArgumentException.class, () -> profile1.removeBill(bill));
    }

    @Test
    @DisplayName("Test getting of total balance")
    public void testTotalBalance() {
        assertEquals(0, profile1.getTotalBalance());
        SpendingsAccount acc1 = new SpendingsAccount("Spending", profile1);
        SpendingsAccount acc2 = new SpendingsAccount("Savings", profile1);
        acc1.add(1000);
        acc2.add(1);
        profile1.addAccount(acc1);
        profile1.addAccount(acc2);
        assertEquals(1001, profile1.getTotalBalance());
    }

    @Test
    @DisplayName("Test adding account to a profile. Should throw if trying to add same account twice, if trying to add an account with the same name, or if the account is not owned by the corresponding profile")
    public void testAddAccount() {
        SavingsAccount testAccount = new SavingsAccount("TestAccount", profile1);
        SavingsAccount duplicateAccount = new SavingsAccount("TestAccount", profile1);
        SavingsAccount testAccount2 = new SavingsAccount("TestAccount2", profile2);

        profile1.addAccount(testAccount);
        assertEquals(testAccount, profile1.getAccounts().get(0));

        assertThrows(IllegalArgumentException.class, () -> profile1.addAccount(testAccount));

        assertThrows(IllegalArgumentException.class, () -> profile1.addAccount(duplicateAccount));

        assertThrows(IllegalArgumentException.class, () -> profile1.addAccount(testAccount2));
    }

    @Test
    @DisplayName("Test if account gets removed from this profile")
    public void testRemoveAccount() {
        SavingsAccount testAccount = new SavingsAccount("TestAccount", profile1);
        SavingsAccount testAccount2 = new SavingsAccount("TestAccount2", profile1);
        profile1.addAccount(testAccount);
        profile1.addAccount(testAccount2);

        profile1.removeAccount(testAccount2);
        assertTrue(profile1.getAccounts().size() == 1);

    }

    @Test
    @DisplayName("Test preview in balance")
    public void testPreviewInBalance() {
        SpendingsAccount acc1 = new SpendingsAccount("Spending", profile1);
        profile1.addAccount(acc1);
        profile1.getAccounts().get(0).add(1000);
        SpendingsAccount acc2 = new SpendingsAccount("NTNU", profile2);
        acc1.changePreview();
        acc2.changePreview();

        Bill bill = new Bill(100, "billName", "NTNU", acc2, acc1,
                profile1);
        profile1.addBill(bill);

        assertTrue(profile1.previewBalance() == 900);
    }

}
