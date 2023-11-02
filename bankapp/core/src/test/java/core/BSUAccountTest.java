package core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import core.Accounts.BSUAccount;
import core.Accounts.SpendingsAccount;

public class BSUAccountTest {
  private Profile profile;
  private BSUAccount bsuAccount;

  @BeforeEach
  @DisplayName("Setting up the profile and account")
  public void setUp() {
    profile = new Profile("Justin Bieber", "Justin@gmail.com", "42375690", "passord123");
    bsuAccount = new BSUAccount("My BSU-account", profile);
    profile.addAccount(bsuAccount);
  }

  @Test
  @DisplayName("Tests if the constructor makes an account with name equal to the input")
  public void testConstructor() {
    BSUAccount newAcc = new BSUAccount("BSU1", profile);
    String name = newAcc.getName();
    assertEquals("BSU1", name);
  }

  @Test
  @DisplayName("Tests if the transaction instructions works. Should throw an IllegalArgumentException if user tries to transfer from BSU-Account")
  public void testTransferTo() throws IOException {
    SpendingsAccount spendingsAccount = new SpendingsAccount("Spendingsaccount", profile);
    profile.addAccount(spendingsAccount);
    spendingsAccount.add(4000);

    bsuAccount.transferFrom(spendingsAccount, 100);
    assertEquals(100, bsuAccount.getBalance());

    assertThrows(IllegalArgumentException.class, () -> spendingsAccount.transferFrom(bsuAccount, 50));
  }

  @Test
  @DisplayName("Tests if account number is generated correctly")
  public void testAccountNr() {
    BSUAccount acc1 = new BSUAccount("acc1", profile);
    BSUAccount acc2 = new BSUAccount("acc2", profile);
    BSUAccount acc3 = new BSUAccount("acc3", profile);
    BSUAccount acc4 = new BSUAccount("acc4", profile);

    assertFalse(acc1.getAccNr() == acc2.getAccNr());
    assertFalse(acc1.getAccNr() == acc3.getAccNr());
    assertFalse(acc1.getAccNr() == acc4.getAccNr());

    assertFalse(acc2.getAccNr() == acc3.getAccNr());
    assertFalse(acc2.getAccNr() == acc4.getAccNr());

    assertFalse(acc3.getAccNr() == acc4.getAccNr());

    assertEquals(acc1.getAccNr().length(), 13);
  }

}
