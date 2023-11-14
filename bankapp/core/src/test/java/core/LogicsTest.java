package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.accounts.SpendingsAccount;

public class LogicsTest {

  private Profile profile1;
  private Profile profile2;
  private SpendingsAccount account1;
  private SpendingsAccount account2;

  @BeforeEach
  @DisplayName("Setting up profiles and accounts")
  public void setup() {
    profile1 = new Profile("Ola Nordmann", "Ola@ntnu.no", "40123456", "Passord1");
    profile2 = new Profile("Kari Nordmann", "Kari@ntnu.no", "40654321", "Passord2");
    account1 = new SpendingsAccount("Useraccount", profile1);
    profile1.addAccount(account1);
    account2 = new SpendingsAccount("Useraccount", profile2);
    profile2.addAccount(account2);
    account1.add(500);
    account2.add(1000);

  }

  @Test
  @DisplayName("Test getting a transactions reversed")
  public void testGetReveredTransactionsArray() {
    Transaction transaction1 = new Transaction(profile1.getEmail(), account2.getAccNr(),
        account2.getProfile().getName(),
        account1.getAccNr(), 30, "(From transfer)");
    Transaction transaction2 = new Transaction(profile1.getEmail(), account2.getAccNr(),
        account2.getProfile().getName(),
        account1.getAccNr(), 18, "(From transfer)");
    Transaction transaction3 = new Transaction(profile2.getEmail(), account1.getAccNr(),
        account1.getProfile().getName(),
        account2.getAccNr(), 25, "(From transfer)");

    List<Transaction> transactions = new ArrayList<>();
    transactions.add(transaction1);
    transactions.add(transaction2);
    transactions.add(transaction3);

    assertEquals(transaction3, Logics.getReveredTransactionsArray(transactions)[0]);
    assertEquals(transaction2, Logics.getReveredTransactionsArray(transactions)[1]);
    assertEquals(transaction1, Logics.getReveredTransactionsArray(transactions)[2]);
  }

  @Test
  @DisplayName("Test finding a specific spendingsaccount by accountnumber, among a list of profiles")
  public void testFindOverallSpendingsAccount() {
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile1);
    profiles.add(profile2);

    assertEquals(account1, Logics.findOverallSpendingsAccount(account1.getAccNr(), profiles));
    assertFalse(account1.equals(Logics.findOverallSpendingsAccount(account2.getAccNr(), profiles)));
  }

  @Test
  @DisplayName("Check if profile exists with the given password")
  public void testCheckProfile() {
    assertEquals(profile1, Logics.checkProfile(profile1, "Passord1"));
    assertThrows(IllegalArgumentException.class, () -> Logics.checkProfile(profile1, "Passord2"));
  }

  @Test
  @DisplayName("Check if a profile is already registered")
  public void testCheckAlreadyRegistered() {
    List<Profile> profiles = new ArrayList<>();
    profiles.add(profile1);
    profiles.add(profile2);

    assertThrows(IllegalArgumentException.class,
        () -> Logics.checkAlreadyRegistered(profiles, "Ola@ntnu.no", "93456789"));
    assertThrows(IllegalArgumentException.class,
        () -> Logics.checkAlreadyRegistered(profiles, "Nina@ntnu.no", "40123456"));
    assertThrows(IllegalArgumentException.class,
        () -> Logics.checkAlreadyRegistered(profiles, "Kari@ntnu.no", "40654321"));
  }
}
