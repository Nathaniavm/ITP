package core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import core.accounts.SavingsAccount;
import core.accounts.SpendingsAccount;

public class ProfileTest {
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;
    private SpendingsAccount acc1;

    @BeforeEach
    @DisplayName("setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("Petter Pan", "peter@ntnu.no", "98765432", "passord111");
        profile2 = new Profile("Charles Darwin", "charles@ntnu.no", "99997722", "JegElskerITP123");
        profile3 = new Profile("Muhammed Ali", "ali@ntnu.no", "92457233", "loneyku9");

        acc1 = new SpendingsAccount("Spending1", profile1);
        profile1.addAccount(acc1);
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
        assertEquals(1, profile1.getAccounts().size());
        SpendingsAccount acc = new SpendingsAccount("SavingsTestCreate", profile1);
        profile1.addAccount(acc);
        assertEquals(2, profile1.getAccounts().size());
        assertEquals("SavingsTestCreate", acc.getName());
    }

    @Test
    @DisplayName("Test getting of total balance")
    public void testTotalBalance() {
        assertEquals(0, profile1.getTotalBalance());
        SpendingsAccount acc2 = new SpendingsAccount("Savings", profile1);
        acc1.add(1000);
        acc2.add(1);
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
        assertEquals(testAccount, profile1.getAccounts().get(1));

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
        assertTrue(profile1.getAccounts().size() == 2);

    }

    @Test
    @DisplayName("Test finding accounts without a bankcard")
    public void testAccountsWithoutBankcards() {
        SpendingsAccount acc2 = new SpendingsAccount("Spending2", profile1);
        SpendingsAccount acc3 = new SpendingsAccount("Spending3", profile1);
        profile1.addAccount(acc2);
        profile1.addAccount(acc3);

        assertTrue(profile1.accountsWithoutBankcards().size() == 3);

        acc1.createBankCard();

        assertTrue(profile1.accountsWithoutBankcards().size() == 2);
        assertEquals(acc2.getAccNr(), profile1.accountsWithoutBankcards().get(0));
    }

    @Test
    @DisplayName("Test finding accounts without a blocked bankcard")
    public void testGetListOfNotBlockedAccNrBankCards() {
        SpendingsAccount acc2 = new SpendingsAccount("Spending2", profile1);
        SpendingsAccount acc3 = new SpendingsAccount("Spending3", profile1);
        profile1.addAccount(acc2);
        profile1.addAccount(acc3);

        assertTrue(profile1.getListOfNotBlockedAccNrBankCards().size() == 0);

        acc1.createBankCard();

        assertTrue(profile1.getListOfNotBlockedAccNrBankCards().size() == 1);
        assertEquals(acc1.getAccNr(), profile1.getListOfNotBlockedAccNrBankCards().get(0));
    }

    @Test
    @DisplayName("Test finding accounts with a blocked bankcard")
    public void testGetListOfBlockedAccNrBankCards() {
        SpendingsAccount acc2 = new SpendingsAccount("Spending2", profile1);
        SpendingsAccount acc3 = new SpendingsAccount("Spending3", profile1);
        profile1.addAccount(acc2);
        profile1.addAccount(acc3);

        assertTrue(profile1.getListOfBlockedAccNrBankCards().size() == 0);

        acc1.createBankCard();
        assertTrue(profile1.getListOfNotBlockedAccNrBankCards().size() == 1);

        acc1.getBankCard().blockCard();
        assertTrue(profile1.getListOfBlockedAccNrBankCards().size() == 1);
        assertEquals(acc1.getAccNr(), profile1.getListOfBlockedAccNrBankCards().get(0));
    }

    @Test
    @DisplayName("Test getting a bankcard")
    public void testGetBankcard() {
        assertThrows(NullPointerException.class, () -> profile1.getBankCard(acc1.getAccNr()));

        acc1.createBankCard();
        assertEquals(acc1.getBankCard(), profile1.getBankCard(acc1.getAccNr()));
    }

    @Test
    @DisplayName("Test finding a spendingsaccount")
    public void testFindSpendingsAccount() {
        SavingsAccount acc2 = new SavingsAccount("Spending2", profile1);
        profile1.addAccount(acc2);

        assertThrows(IllegalArgumentException.class, () -> profile1.findSpendingsAccount(acc2.getAccNr()));

        assertEquals(acc1, profile1.findSpendingsAccount(acc1.getAccNr()));
    }

}
