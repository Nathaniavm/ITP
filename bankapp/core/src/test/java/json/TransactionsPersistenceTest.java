package json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import core.Profile;
import core.Transaction;
import core.accounts.SpendingsAccount;

public class TransactionsPersistenceTest {

    private static final String currentDir = System.getProperty("user.dir");
    private final static String filename = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";
    private final static String fakefile = "fakefile.json";

    private Profile profile1;
    private Profile profile2;
    private SpendingsAccount account1;
    private SpendingsAccount account2;
    private Transaction transaction1;

    @BeforeEach
    @DisplayName("Setting up profiles and accounts")
    public void setup() throws StreamWriteException, DatabindException, IOException {
        TransactionsPersistence.clearFile(filename);

        profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
        profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
        account1 = new SpendingsAccount("Useraccount", profile1);
        profile1.addAccount(account1);
        account2 = new SpendingsAccount("Useraccount", profile2);
        profile2.addAccount(account2);
        account1.add(500);
        account2.add(1000);
        transaction1 = new Transaction(profile1.getEmail(), account2.getAccNr(), account2.getProfile().getName(),
                account1.getAccNr(), 100, "(From transfer)");

    }

    @Test
    @DisplayName("Testing if application throws IOException if path does not exist")
    public void testFakeFile() {
        assertThrows(IOException.class, () -> TransactionsPersistence.writeTransactions(transaction1, fakefile));
        assertThrows(IOException.class, () -> TransactionsPersistence.readTransactions(fakefile));
    }

    @Test
    @DisplayName("Tests if written to file")
    public void testWriteTransactions() throws IOException {
        TransactionsPersistence.writeTransactions(transaction1, filename);
        List<Transaction> transactionsList = TransactionsPersistence.readTransactions(filename);
        assertEquals(profile1.getEmail(), transactionsList.get(0).getEmail());
    }

    @Test
    @DisplayName("Tests if read correctly")
    public void testReadTransactions() throws IOException {
        TransactionsPersistence.writeTransactions(transaction1, filename);
        List<Transaction> transactionsList = TransactionsPersistence.readTransactions(filename);
        assertEquals(profile1.getEmail(), transactionsList.get(0).getEmail());
        assertEquals(account2.getAccNr(), transactionsList.get(0).getTransactionTo());
        assertEquals(account2.getProfile().getName(), transactionsList.get(0).getName());
        assertEquals(account1.getAccNr(), transactionsList.get(0).getTransactionFrom());
        assertTrue(100 == transaction1.getAmount());
    }

    @Test
    @DisplayName("Tests if it finds the proper profile's transactions")
    public void testGetProfilesTransaction() throws IOException {
        Transaction transaction2 = new Transaction(profile2.getEmail(), account1.getAccNr(),
                account1.getProfile().getName(),
                account2.getAccNr(), 100, "(From transfer)");
        Transaction transaction3 = new Transaction(profile1.getEmail(), account2.getAccNr(),
                account2.getProfile().getName(),
                account1.getAccNr(), 455, "(From transfer)");
        TransactionsPersistence.writeTransactions(transaction1, filename);
        TransactionsPersistence.writeTransactions(transaction2, filename);
        TransactionsPersistence.writeTransactions(transaction3, filename);

        List<Transaction> profile1sTransactions;

        profile1sTransactions = TransactionsPersistence.getProfilesTransaction(profile1, filename);

        assertTrue(2 == profile1sTransactions.size());
        assertEquals(profile1.getEmail(), profile1sTransactions.get(0).getEmail());
        assertEquals(profile1.getEmail(), profile1sTransactions.get(1).getEmail());

    }

}
