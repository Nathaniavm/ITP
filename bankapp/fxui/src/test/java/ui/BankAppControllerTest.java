package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import core.Profile;
import core.Accounts.SpendingsAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Unit test for BankAppController
*/
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BankAppControllerTest extends ApplicationTest {
  private BankAppController controller;
  private Parent root;
  Profile NTNU;

  private void setUp() {
    NTNU = new Profile("NTNU Gløshaugen", "NTNU@ntnu.no", "98989898", "Administrator59");
    NTNU.addAccount(new SpendingsAccount("NTNU", NTNU));
  }

  @Order(1)
  @Override
  @DisplayName("Loading the stage")
  public void start(Stage stage) throws IOException {
    controller = new BankAppController();
    setUp();
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @Nested
  public class testMainPages {
    @BeforeEach
    @DisplayName("Logging in to the Ada Lovelace profile")
    private void loginSetUp() {
      clickOn("#emailInput").write("adalovelace@gmail.no");
      clickOn("#passwordInput").write("koding1234");
      clickOn("#loginButton");
    }

    @Order(2)
    @Test
    @DisplayName("Testing if the spending page is loaded correctly")
    public void testSpendingTab() {
      clickOn("#spendingTab");
      FxAssert.verifyThat("#spending", NodeMatchers.isVisible());
      FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
    }

    @Order(3)
    @Test
    @DisplayName("Testing if the payment page is loaded correctly")
    public void testPaymentTab() {
      clickOn("#paymentsTab");
      FxAssert.verifyThat("#payments", NodeMatchers.isVisible());

      clickOn("#goToTransferButton");
      FxAssert.verifyThat("#transfer", NodeMatchers.isVisible());
      clickOn("#paymentsTab");

      clickOn("#goToPayButton");
      FxAssert.verifyThat("#pay", NodeMatchers.isVisible());
    }

    @Order(4)
    @Test
    @DisplayName("Testing if the savings page is loaded correctly")
    public void testSavingsTab() {
      clickOn("#savingsTab");
      FxAssert.verifyThat("#savings", NodeMatchers.isVisible());

      FxAssert.verifyThat("#totalBalance", hasText("100"));
      FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
    }

    @Order(5)
    @Test
    @DisplayName("Testing if the profile page is loaded correctly")
    public void testProfileTab() {
      clickOn("#profileTab");
      FxAssert.verifyThat("#profile", NodeMatchers.isVisible());
    }

    @Order(6)
    @Test
    @DisplayName("Testing if a new account is made correctly when the input is valid, and not made it the inputs are unvalid")
    public void testMakeAccount() {
      clickOn("#newAccountButton");
      FxAssert.verifyThat("#newAccount", NodeMatchers.isVisible());

      clickOn("#giveAccountName").write("Brukerkonto2");
      clickOn("#createAccountButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Brukerkonto2"))
          .findAny().isPresent());

      clickOn("#selectAccountType");
      clickOn("Savings account");
      clickOn("#giveAccountName").write("Savings");
      clickOn("#createAccountButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Savings"))
          .findAny().isPresent());
      clickOn("#selectAccountType");
      clickOn("Savings account");
      clickOn("#giveAccountName").write("Savings2");
      clickOn("#createAccountButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Savings2"))
          .findAny().isPresent());

      clickOn("#selectAccountType");
      clickOn("BSU");
      clickOn("#giveAccountName").write("BSU");
      clickOn("#createAccountButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("BSU"))
          .findAny().isPresent());
    }

    @Order(7)
    @Test
    @DisplayName("Testing if an account is deleted correctly for already exsisting account, and that you get an error if you try to delete an account that doesn't exist")
    public void testDeleteAccount() {
      clickOn("#deleteAccountButton");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
      clickOn("#deleteAccountName").write("Savings2");
      clickOn("#deleteAccountNow");
      assertFalse(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Savings2"))
          .findAny().isPresent());
      FxAssert.verifyThat("#overview", NodeMatchers.isVisible());

      clickOn("#deleteAccountButton");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
      clickOn("#deleteAccountName").write("Savings2");
      clickOn("#deleteAccountNow");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
    }

    @Order(8)
    @Test
    @DisplayName("Testing a savings account")
    public void testSavingsAccount() {
      // kanke paye bills, og kanke ha bankkort

    }

    @Order(9)
    @Test
    @DisplayName("Testing a BSU account")
    public void testBSUaccount() {
      // kanke paye bills, og kanke ha bankkort

    }

    @Order(10)
    @Test
    @DisplayName("Testing if the transfers transfers money correctly between different accounts")
    public void testTransferMoney() {
      clickOn("#paymentsTab");
      clickOn("#goToTransferButton");
      FxAssert.verifyThat("#transfer", NodeMatchers.isVisible());

      clickOn("#transferAmount").write("50");
      clickOn("#transferFromChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Spendings account")).findFirst().get().getAccNr());
      clickOn("#transferToChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Brukerkonto2")).findFirst().get().getAccNr());
      clickOn("#transferButton");

      clickOn("#homeTab");
      FxAssert.verifyThat("#spendingAccountBalance", hasText("50"));
      clickOn("#savingsTab");
      FxAssert.verifyThat("#totalBalance", hasText("100"));

      clickOn("#paymentsTab");
      clickOn("#goToTransferButton");

      clickOn("#transferAmount").write("50");
      clickOn("#transferFromChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Brukerkonto2")).findFirst().get().getAccNr());
      clickOn("#transferToChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Savings")).findFirst().get().getAccNr());
      clickOn("#transferButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Savings"))
          .findFirst().get().getBalance() == 50);
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Brukerkonto2"))
          .findFirst().get().getBalance() == 0);

      clickOn("#transferAmount").write("50");
      clickOn("#transferFromChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Savings")).findFirst().get().getAccNr());
      clickOn("#transferToChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("BSU")).findFirst().get().getAccNr());
      clickOn("#transferButton");
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("BSU")).findFirst()
          .get().getBalance() == 50);
      assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Savings"))
          .findFirst().get().getBalance() == 0);
    }

    @Order(11)
    @Test
    @DisplayName("Testing if one pays money to the correct account, and that the profile updates accordingly")
    public void testPayMoney() {
      clickOn("#paymentsTab");
      clickOn("#goToPayButton");
      FxAssert.verifyThat("#pay", NodeMatchers.isVisible());

      clickOn("#payAmount").write("20");
      clickOn("#payFromChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Spendings account"))
          .findAny().get().getAccNr());
      clickOn("#payTo").write("1234 77 99570"); // bruker en profile som allerede er lagret i filen
      clickOn("#payButton");
      FxAssert.verifyThat("#pay", NodeMatchers.isVisible());
      assertTrue(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Spendings account")).findAny().get().getBalance() == 30);
      clickOn("#homeTab");
      FxAssert.verifyThat("#spendingAccountBalance", hasText("30"));
      clickOn("#savingsTab");
      FxAssert.verifyThat("#totalBalance", hasText("80"));
    }

    @Nested
    @DisplayName("Test the different bill functions")
    public class testBill {
      @BeforeEach
      @DisplayName("Loads the payment tab")
      public void billSetUp() {
        clickOn("#paymentsTab");
      }

      @Order(12)
      @Test
      @DisplayName("Testing if a bill is made corecctly")
      public void testMakeBill() {
        clickOn("#newBillButton");
        FxAssert.verifyThat("#pay", NodeMatchers.isVisible());

        clickOn("#billName").write("leie");
        clickOn("#billAmount").write("10");
        clickOn("#payerAccountChoiceBox");
        clickOn(controller.getProfile().getAccounts().stream()
            .filter(a -> a.getName().equals("Spendings account")).findAny().get().getAccNr());
        clickOn("#sellerAccount").write("1234 77 99570"); // bruker en profile som allerede er lagret i filen
        clickOn("#setNewBillButton");

        clickOn("#homeTab");
        FxAssert.verifyThat("#spendingAccountBalance", hasText("30"));
        clickOn("#savingsTab");
        FxAssert.verifyThat("#totalBalance", hasText("80"));

        assertTrue(controller.getProfile().getBills().stream().filter(a -> a.getBillName().equals("leie"))
            .findAny().get().getAmount() == 10);
      }

      // @Test
      // public void testPayBill() {

      // }
    }

    @Nested
    @DisplayName("Test if a profile is updated accordingly")
    public class testProfile {
      @BeforeEach
      @DisplayName("Loads the profile tab, and goes into the settings")
      private void profileSetUp() {
        clickOn("#profileTab");
        clickOn("#settingsButton");
      }

      @Order(13)
      @Test
      @DisplayName("Testing if the profile updates accordingly to valid and unvalid inputs")
      public void testProfileChange() {
        FxAssert.verifyThat("#settings", NodeMatchers.isVisible());
        clickOn("#changeNumberTo").write("45456677");
        // clickOn("#changeEmailTo").write("al@gmail.no");
        clickOn("#changePasswordTo").write("koding4321");
        clickOn("#confirmChangePassword").write("koding4321");
        clickOn("#updateSettings");

        assertTrue(controller.getProfile().getTlf().equals("45456677"));
        assertTrue(controller.getProfile().getPassword().equals("koding4321"));

        clickOn("#profileTab");
        clickOn("#settingsButton");
        clickOn("#changeNumberTo").write("47765");
        clickOn("#updateSettings");
        assertFalse(controller.getProfile().getTlf().equals("47765"));

        clickOn("#profileTab");
        clickOn("#settingsButton");
        clickOn("#changeNumberTo").write("98987765");
        // clickOn("#changeEmailTo").write("adalovelace@gmail.no");
        clickOn("#changePasswordTo").write("koding1234");
        clickOn("#confirmChangePassword").write("koding1234");
        clickOn("#updateSettings");
      }

      @Order(14)
      @Test
      @DisplayName("Testing if a profile is deleted")
      public void testDeleteProfile() {
        clickOn("#deleteProfileButton");
        FxAssert.verifyThat("#login", NodeMatchers.isVisible());
      }
    }
  }

  @Nested
  @DisplayName("Testing that the signup function works accordingly")
  public class testRegisterButtons {
    @BeforeEach
    @DisplayName("Load the signup page")
    private void registerSetUp() {
      clickOn("#signUpButton");
    }

    @Order(15)
    @Test
    @DisplayName("Testing if a profile gets made accordingly")
    public void testHandleSignUpClick() {
      FxAssert.verifyThat("#register", NodeMatchers.isVisible());
      clickOn("#fullName").write("Ada Lovelace");
      clickOn("#email").write("adalovelace@gmail.no");
      clickOn("#phoneNr").write("98987765");
      clickOn("#password").write("koding1234");
      clickOn("#passwordConfirm").write("koding1234");
      clickOn("#registerButton");

      assertTrue(controller.getProfile().getName().equals("Ada Lovelace"));
      assertTrue(controller.getProfile().getEmail().equals("adalovelace@gmail.no"));
      assertTrue(controller.getProfile().getTlf().equals("98987765"));
      assertTrue(controller.getProfile().getPassword().equals("koding1234"));
    }

    @Order(16)
    @Test
    @DisplayName("Testing if the back arrow works")
    public void testHandleBackArrow() {
      clickOn("#backArrow");
      FxAssert.verifyThat("#login", NodeMatchers.isVisible());
    }
  }
}