package json;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import core.Account;
import core.Bill;
import core.Profile;

public class ProfileInformationManagementTest {

    private Profile profile1;
    private Profile profile2;
    private List<Profile> profiles;

    private Account acc1;
    private Account acc2;

    private Bill bill1;

    private static final String currentDir = System.getProperty("user.dir");
    private static final String file = currentDir + "/src/test/java/json/ProfileInformationTest.json";
    private static final String fakeFile = "fakeFile.json";

    private final static String filename2 = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";

    @Before
    @DisplayName("Setting up the different profiles")
    public void setUp() {
        profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
        profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
        acc1 = new Account("James", profile1);
        profile1.addAccount(acc1);
        acc2 = new Account("Heui", profile2);
        profile2.addAccount(acc2);
        bill1 = new Bill(150, "Groceries", "Ola Nordmann", acc1, acc2, profile1);
        profile1.addBill(bill1);

    }

    @Test
    @DisplayName("Testing if application throws IOException if path does not exist")
    public void testFakeFile() {
        assertThrows(IOException.class, () -> ProfileInformationManagement.writeInformationToFile(profile1, fakeFile));
    }

    @Test
    @DisplayName("Tests if correct information is written to file")
    public void testCorrectInformationWrittenToFile() throws StreamWriteException, DatabindException, IOException {
        System.out.println(System.getProperty("user.dir"));
        ProfileInformationManagement.writeInformationToFile(profile1, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals("Ola Nordmann", profiles.get(0).getName());
        assertEquals("Ola@ntnu.no", profiles.get(0).getEmail());
        assertEquals("40123456", profiles.get(0).getTlf());
        assertEquals("Passord1", profiles.get(0).getPassword());
        assertEquals(acc1.getAccNr(), profiles.get(0).getAccounts().get(0).getAccNr());
        assertEquals(bill1.getAmount(), profiles.get(0).getBills().get(0).getAmount());
    }

    @Test
    @DisplayName("Tests if old profile and corresponding information does not get overwritten")
    public void testOverwriting() throws StreamWriteException, DatabindException, IOException {
        ProfileInformationManagement.writeInformationToFile(profile1, file);
        ProfileInformationManagement.writeInformationToFile(profile2, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals("Ola Nordmann", profiles.get(0).getName());
        assertEquals("Ola@ntnu.no", profiles.get(0).getEmail());
        assertEquals("40123456", profiles.get(0).getTlf());
        assertEquals("Passord1", profiles.get(0).getPassword());
        assertEquals(acc1.getAccNr(), profiles.get(0).getAccounts().get(0).getAccNr());
        assertEquals(bill1.getAmount(), profiles.get(0).getBills().get(0).getAmount());

        assertEquals("Kari Nordmann", profiles.get(1).getName());
        assertEquals("Kari@ntnu.no", profiles.get(1).getEmail());
        assertEquals("40654321", profiles.get(1).getTlf());
        assertEquals("Passord2", profiles.get(1).getPassword());
        assertEquals(acc2.getAccNr(), profiles.get(1).getAccounts().get(0).getAccNr());

    }

    @Test
    @DisplayName("Tests if information about accounts are saved")
    public void testAccount() throws StreamWriteException, DatabindException, IOException {
        acc1.add(100);
        acc2.add(10000);

        ProfileInformationManagement.writeInformationToFile(profile1, file);
        ProfileInformationManagement.writeInformationToFile(profile2, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals(100, profiles.get(0).getAccounts().get(0).getBalance());
        assertEquals(10000, profiles.get(1).getAccounts().get(0).getBalance());
        assertEquals("Ola Nordmann", profiles.get(0).getAccounts().get(0).getProfile().getName());
        assertEquals("Kari Nordmann", profiles.get(1).getAccounts().get(0).getProfile().getName());
    }

    @Test
    @DisplayName("Tests if information in file is right after paying bills and if the bill gets removed after paying")
    public void testBills() throws StreamWriteException, DatabindException, IOException {
        acc1.add(200); // seller
        acc2.add(150); // payer
        bill1.pay(filename2);

        ProfileInformationManagement.writeInformationToFile(profile1, file);
        ProfileInformationManagement.writeInformationToFile(profile2, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals(350, profiles.get(0).getAccounts().get(0).getBalance());
        assertEquals(0, profiles.get(1).getAccounts().get(0).getBalance());

        assertEquals(0, profiles.get(0).getBills().size());
    }

    @Test
    @DisplayName("Test exception if bill is larger that money in account")
    public void testBillException() throws StreamWriteException, DatabindException, IOException {
        acc1.add(200); // seller
        acc2.add(100); // payer

        ProfileInformationManagement.writeInformationToFile(profile1, file);
        ProfileInformationManagement.writeInformationToFile(profile2, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertThrows(IllegalArgumentException.class, () -> bill1.pay(filename2));
    }

    @Test
    @DisplayName("Test if bankcard gets written in to file correctly")
    public void testBankCardUpdated() throws StreamWriteException, DatabindException, IOException {
        acc1.createBankCard();
        ProfileInformationManagement.writeInformationToFile(profile1, file);

        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals(acc1.getBankCard().getCardholder(),
                profiles.get(0).getAccounts().get(0).getBankCard().getCardholder());
    }

}
