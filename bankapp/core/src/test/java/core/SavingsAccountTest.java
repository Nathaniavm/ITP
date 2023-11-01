package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Accounts.SavingsAccount;

public class SavingsAccountTest {

  private Profile profile;
  private SavingsAccount sAccount;
  private static final String currentDir = System.getProperty("user.dir");
  private final static String filename = currentDir + "/src/test/java/json/TransactionsOverviewTest.json";

  @BeforeEach
  @DisplayName("Setting up the profile and account")
  public void setUp() {
    profile = new Profile("Kari Nordmann", "kari@gmail.com", "12345690", "passord123");
    sAccount = new SavingsAccount("My Savingsaccount", profile);
  }

  @Test
  @DisplayName("Tests if the constructor makes an account with name equal to the input")
  public void testConstructor() {
    SavingsAccount newAcc = new SavingsAccount("Acc1", profile);
    String name = newAcc.getName();
    assertEquals("Acc1", name);
  }

  @Test
  @DisplayName("Tests if the transaction instructions works")
  public void testBalance() {
    sAccount.add(1500);
    sAccount.remove(500);
    assertEquals(1000, sAccount.getBalance());
  }

  @Test
  @DisplayName("Tests transaction between two accounts, and if there will be an IllegalArgumentException when we try to tranfer more money than the balance in the account")
  public void testTransfer() throws IOException {
    SavingsAccount newAcc = new SavingsAccount("Acc1", profile);
    newAcc.add(100);

    assertThrows(IllegalArgumentException.class, () -> newAcc.transferTo(sAccount, 150, filename));
    assertTrue(sAccount.getBalance() == 0);

    sAccount.add(200);
    newAcc.transferTo(sAccount, 50, filename);
    assertTrue(sAccount.getBalance() == newAcc.getBalance());
  }

  @Test
  @DisplayName("Tests if there will be an NullPointerException if the account is null")
  public void testNull() {
    SavingsAccount nullAccount = null;
    sAccount.add(500);
    assertThrows(NullPointerException.class, () -> sAccount.transferTo(nullAccount, 100, filename));
  }

  @Test
  @DisplayName("Tests if there will be an IllegalArgumentException if account tries to transfer to itself")
  public void testTranserToSelf() {
    sAccount.add(500);
    assertThrows(IllegalArgumentException.class, () -> sAccount.transferTo(sAccount, 100, filename));
  }

  @Test
  @DisplayName("Tests if account number is generated correctly")
  public void testAccountNr() {
    SavingsAccount acc1 = new SavingsAccount("acc1", profile);
    SavingsAccount acc2 = new SavingsAccount("acc2", profile);
    SavingsAccount acc3 = new SavingsAccount("acc3", profile);
    SavingsAccount acc4 = new SavingsAccount("acc4", profile);

    assertFalse(acc1.getAccNr() == acc2.getAccNr());
    assertFalse(acc1.getAccNr() == acc3.getAccNr());
    assertFalse(acc1.getAccNr() == acc4.getAccNr());

    assertFalse(acc2.getAccNr() == acc3.getAccNr());
    assertFalse(acc2.getAccNr() == acc4.getAccNr());

    assertFalse(acc3.getAccNr() == acc4.getAccNr());

    assertEquals(acc1.getAccNr().length(), 13);
  }

}
