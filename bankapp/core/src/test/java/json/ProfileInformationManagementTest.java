package json;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import core.Profile;
import core.accounts.SpendingsAccount;

public class ProfileInformationManagementTest {

    private Profile profile1;
    private Profile profile2;
    private List<Profile> profiles;

    private SpendingsAccount acc1;
    private SpendingsAccount acc2;

    private static final String currentDir = System.getProperty("user.dir");
    private static final String file = currentDir + "/src/test/java/json/ProfileInformationTest.json";
    private static final String fakeFile = "fakeFile.json";

    private final static String filename2 = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";

    @BeforeEach
    @DisplayName("Setting up the different profiles")
    public void setUp() throws StreamReadException, DatabindException, IOException {
        ProfileInformationManagement.clearFile(file);
        TransactionsPersistence.clearFile(filename2);
        profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
        profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
        acc1 = new SpendingsAccount("James", profile1);
        profile1.addAccount(acc1);
        acc2 = new SpendingsAccount("Heui", profile2);
        profile2.addAccount(acc2);
    }

    @Test
    @DisplayName("Testing if application throws IOException if path does not exist")
    public void testFakeFile() {
        assertThrows(IOException.class, () -> ProfileInformationManagement.writeInformationToFile(profile1, fakeFile));
        assertThrows(IOException.class, () -> ProfileInformationManagement.readFromFile(fakeFile));
        assertThrows(IOException.class, () -> ProfileInformationManagement.deleteProfile(fakeFile, profile1));

    }

    @Test
    @DisplayName("Tests if correct information is written to file")
    public void testCorrectInformationWrittenToFile() throws StreamWriteException, DatabindException, IOException {
        ProfileInformationManagement.writeInformationToFile(profile1, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals("Ola Nordmann", profiles.get(0).getName());
        assertEquals("Ola@ntnu.no", profiles.get(0).getEmail());
        assertEquals("40123456", profiles.get(0).getTlf());
        assertEquals("Passord1", profiles.get(0).getPassword());
        assertEquals(acc1.getAccNr(), profiles.get(0).getAccounts().get(0).getAccNr());

        profile1.changePassword("NyttPassord123");
        ProfileInformationManagement.writeInformationToFile(profile1, file);
        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals("NyttPassord123", profiles.get(0).getPassword());
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
    @DisplayName("Test if bankcard gets written in to file correctly")
    public void testBankCardUpdated() throws StreamWriteException, DatabindException, IOException {
        acc1.createBankCard();
        ProfileInformationManagement.writeInformationToFile(profile1, file);

        profiles = new ArrayList<>(ProfileInformationManagement.readFromFile(file));

        assertEquals(acc1.getBankCard().getCardholder(),
                ((SpendingsAccount) profiles.get(0).getAccounts().get(0)).getBankCard().getCardholder());
    }

    @Test
    @DisplayName("Test if profile gets deleted. Should throw IOException if file does not exists")
    public void testDeleteProfile() throws StreamReadException, DatabindException, IOException {
        ProfileInformationManagement.writeInformationToFile(profile1, file);
        ProfileInformationManagement.deleteProfile(file, profile1);
        assertTrue(ProfileInformationManagement.readFromFile(file).size() == 0);

    }

}
