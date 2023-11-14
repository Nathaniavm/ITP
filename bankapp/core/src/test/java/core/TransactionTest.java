package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.accounts.SpendingsAccount;

public class TransactionTest {

  private Profile profile1;
  private Profile profile2;
  private SpendingsAccount account1;
  private SpendingsAccount account2;
  private Transaction transaction1;

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
    transaction1 = new Transaction(profile1.getEmail(), account2.getAccNr(), account2.getProfile().getName(),
        account1.getAccNr(), 100,"(From transfer)");

  }

  @Test
  @DisplayName("Tests if constructor constructs objects correct. Shoudl throw an IllegalArgumentException if tried to transfer to an account fram that same account")
  public void testConstructor() {
    Transaction transactionConstructed = new Transaction(profile2.getEmail(), account1.getAccNr(),
        account1.getProfile().getName(),
        account2.getAccNr(), 100,"(From transfer)");
    assertEquals(profile2.getEmail(), transactionConstructed.getEmail());

    assertThrows(IllegalArgumentException.class, () -> new Transaction(profile2.getEmail(), account1.getAccNr(),
        account1.getProfile().getName(), account1.getAccNr(), 100,"(From transfer)"));

  }

  @Test
  @DisplayName("Test getter for email")
  public void testGetEmail() {
    assertEquals(profile1.getEmail(), transaction1.getEmail());
  }

  @Test
  @DisplayName("Test getter for transaction to")
  public void testGetTransactionTo() {
    assertEquals(account2.getAccNr(), transaction1.getTransactionTo());
  }

  @Test
  @DisplayName("Test getter for name")
  public void testGetName() {
    assertEquals(account2.getProfile().getName(), transaction1.getName());
  }

  @Test
  @DisplayName("Test getter for transaction from")
  public void testGetTransactionFrom() {
    assertEquals(account1.getAccNr(), transaction1.getTransactionFrom());
  }

  @Test
  @DisplayName("Test getter for amount")
  public void testGetAmount() {
    assertTrue(100 == transaction1.getAmount());
  }
 @Test
  @DisplayName("Test getter for message")
  public void testGetMessage() {
    assertEquals("(From transfer)", transaction1.getMessage());
  }
}
