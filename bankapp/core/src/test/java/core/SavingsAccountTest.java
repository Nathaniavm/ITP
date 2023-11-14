package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.accounts.SavingsAccount;

public class SavingsAccountTest {

  private Profile profile;
  private SavingsAccount sAccount;

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
  public void testTransfer() {
    SavingsAccount newAcc = new SavingsAccount("Acc1", profile);
    newAcc.add(100);

    assertThrows(IllegalArgumentException.class, () -> newAcc.transferFrom(sAccount, 150));
    assertTrue(sAccount.getBalance() == 0);

    sAccount.add(200);
    newAcc.transferFrom(sAccount, 50);
    assertTrue(sAccount.getBalance() == newAcc.getBalance());
  }

  @Test
  @DisplayName("Tests if there will be an NullPointerException if the account is null")
  public void testNull() {
    SavingsAccount nullAccount = null;
    sAccount.add(500);
    assertThrows(NullPointerException.class, () -> sAccount.transferFrom(nullAccount, 100));
  }

  @Test
  @DisplayName("Tests if the transaction instructions works. Should throw an IllegalArgumentException if user tries to transfer from BSU-Account")
  public void testTransferFrom() throws IOException {
    Profile profile2 = new Profile("Ole Hansen", "Ole@gmail.com", "42375690", "passord123");
    SavingsAccount savings = new SavingsAccount("My Savings", profile2);
    profile2.addAccount(savings);
    savings.add(500);
    SavingsAccount savingsAccount2 = new SavingsAccount("Spendingsaccount", profile);
    profile.addAccount(savingsAccount2);
    savingsAccount2.add(4000);

    sAccount.transferFrom(savingsAccount2, 100);
    assertEquals(100, sAccount.getBalance());

    assertThrows(IllegalArgumentException.class, () -> sAccount.transferFrom(sAccount, 100));

    assertThrows(IllegalArgumentException.class, () -> savingsAccount2.transferFrom(savings, 50));
    assertThrows(IllegalArgumentException.class, () -> savings.transferFrom(savingsAccount2, 50));
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

  @Test
  @DisplayName("Test adding transaction")
  public void addTransaction() {
    SavingsAccount savingsAccount = new SavingsAccount("Spendingsaccount", profile);
    profile.addAccount(savingsAccount);
    savingsAccount.add(4000);

    Profile profile2 = new Profile("Nora Nordmann", "Nora@gmail.com", "42905690", "passord123");
    SavingsAccount savings = new SavingsAccount("My savings", profile2);
    profile2.addAccount(savings);
    savings.add(500);

    Transaction transaction = new Transaction("Justin@gmail.com", sAccount.getAccNr(), "Justin Bieber",
        savingsAccount.getAccNr(), 10,"(From transfer)");
    savingsAccount.addTransaction(transaction);

    Transaction transaction2 = new Transaction("Hailey@gmail.com", sAccount.getAccNr(), "Justin Bieber",
        savings.getAccNr(), 10,"(From transfer)");

    assertEquals(savingsAccount.getAccNr(), savingsAccount.getTransaction().get(0).getTransactionFrom());
    assertEquals(sAccount.getAccNr(), savingsAccount.getTransaction().get(0).getTransactionTo());

    assertThrows(IllegalArgumentException.class, () -> savingsAccount.addTransaction(transaction2));

  }

  @Test
  @DisplayName("Test change preview")
  public void testChangePreview() {
    sAccount.changePreview();
    assertTrue(sAccount.showInPreview());
  }
}
