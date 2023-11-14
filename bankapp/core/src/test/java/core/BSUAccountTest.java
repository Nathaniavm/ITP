package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.accounts.BsuAccount;
import core.accounts.SpendingsAccount;

public class BsuAccountTest {
  private Profile profile;
  private BsuAccount bsuAccount;

  @BeforeEach
  @DisplayName("Setting up the profile and account")
  public void setUp() {
    profile = new Profile("Justin Bieber", "Justin@gmail.com", "42375690", "passord123");
    bsuAccount = new BsuAccount("My BSU-account", profile);
    profile.addAccount(bsuAccount);
  }

  @Test
  @DisplayName("Tests if the constructor makes an account with name equal to the input")
  public void testConstructor() {
    BsuAccount newAcc = new BsuAccount("BSU1", profile);
    String name = newAcc.getName();
    assertEquals("BSU1", name);
  }

  @Test
  @DisplayName("Tests if the transaction instructions works. Should throw an IllegalArgumentException if user tries to transfer from BSU-Account")
  public void testTransferFrom() throws IOException {
    Profile profile2 = new Profile("Hailey Bieber", "Hailey@gmail.com", "42375690", "passord123");
    SpendingsAccount spendings = new SpendingsAccount("My spending", profile2);
    profile2.addAccount(spendings);
    spendings.add(500);
    SpendingsAccount spendingsAccount = new SpendingsAccount("Spendingsaccount", profile);
    profile.addAccount(spendingsAccount);
    spendingsAccount.add(4000);

    bsuAccount.transferFrom(spendingsAccount, 100);
    assertEquals(100, bsuAccount.getBalance());

    assertThrows(IllegalArgumentException.class, () -> spendingsAccount.transferFrom(bsuAccount, 50));

    assertThrows(IllegalArgumentException.class, () -> spendingsAccount.transferFrom(spendings, 50));
    assertThrows(IllegalArgumentException.class, () -> spendings.transferFrom(spendingsAccount, 50));
  }

  @Test
  @DisplayName("Tests if account number is generated correctly")
  public void testAccountNr() {
    BsuAccount acc1 = new BsuAccount("acc1", profile);
    BsuAccount acc2 = new BsuAccount("acc2", profile);
    BsuAccount acc3 = new BsuAccount("acc3", profile);
    BsuAccount acc4 = new BsuAccount("acc4", profile);

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
    SpendingsAccount spendingsAccount = new SpendingsAccount("Spendingsaccount", profile);
    profile.addAccount(spendingsAccount);
    spendingsAccount.add(4000);

    Profile profile2 = new Profile("Hailey Bieber", "Hailey@gmail.com", "42375690", "passord123");
    SpendingsAccount spendings = new SpendingsAccount("My spending", profile2);
    profile2.addAccount(spendings);
    spendings.add(500);

    Transaction transaction = new Transaction("Justin@gmail.com", bsuAccount.getAccNr(), "Justin Bieber",
        spendingsAccount.getAccNr(), 10,"(From transfer)");
    spendingsAccount.addTransaction(transaction);

    Transaction transaction2 = new Transaction("Hailey@gmail.com", bsuAccount.getAccNr(), "Justin Bieber",
        spendings.getAccNr(), 10,"(From transfer)");

    assertEquals(spendingsAccount.getAccNr(), spendingsAccount.getTransaction().get(0).getTransactionFrom());
    assertEquals(bsuAccount.getAccNr(), spendingsAccount.getTransaction().get(0).getTransactionTo());

    assertThrows(IllegalArgumentException.class, () -> spendingsAccount.addTransaction(transaction2));
  }

  @Test
  @DisplayName("Test change preview")
  public void testChangePreview() {
    bsuAccount.changePreview();
    assertTrue(bsuAccount.showInPreview());
  }

}
