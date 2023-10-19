package json;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import core.Account;
import core.Profile;

public class TransactionsTest {

    private static final String currentDir = System.getProperty("user.dir");
    private final static String filename = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";
    private final static String fakefile = "fakefile.json";

    private Profile profile1;
    private Profile profile2;
    private Account account1;
    private Account account2;
    private Transactions transaction1;

    @Before
    @DisplayName("Setting up profiles and accounts")
    public void setup() {
        profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
        profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
        account1 = new Account("Useraccount", profile1);
        profile1.addAccount(account1);
        account2 = new Account("Useraccount", profile2);
        profile2.addAccount(account2);
        account1.add(500);
        account2.add(1000);
        transaction1 = new Transactions(profile1, account2, 100);

    }

    @Test
    @DisplayName("Tests if constructor constructs object correct")
    public void testConstructor() {
        Transactions transaction2 = new Transactions(profile2, account1, 300);
        assertEquals(transaction2.getProfile(), profile2);
        assertEquals(transaction2.getTransactionTo(), account1);
        assertTrue(transaction2.getAmount() == 300);
    }

    @Test
    @DisplayName("Testing if application throws IOException if path does not exist")
    public void testFakeFile() {
        assertThrows(IOException.class, () -> Transactions.writeTransactions(transaction1, fakefile));
        assertThrows(IOException.class, () -> Transactions.readTransactions(fakefile));
    }

    @Test
    @DisplayName("Tests if written to file")
    public void testWriteTransactions() throws IOException {
        System.out.println(transaction1);
        Transactions.writeTransactions(transaction1, filename);
        List<Transactions> transactionsList = Transactions.readTransactions(filename);
        assertEquals(profile1.getName(), transactionsList.get(0).getProfile().getName());
    }

    @Test
    @DisplayName("Tests if read correctly")
    public void testReadTransactions() throws IOException {
    }

    @Test
    @DisplayName("Test getter for the account paid to")
    public void testGetTransferTo() {
        assertEquals(transaction1.getTransactionTo(), account2);
        assertNotEquals(transaction1.getTransactionTo(), account1);
    }

    @Test
    @DisplayName("Test getter for the payer")
    public void testGetProfile() {
        assertEquals(transaction1.getProfile(), profile1);
        assertNotEquals(transaction1.getProfile(), profile2);
    }

    @Test
    @DisplayName("Test getter for the amount")
    public void testGetAmount() {
        assertTrue(transaction1.getAmount() == 100);
        assertFalse(transaction1.getAmount() == 300);
    }

}
