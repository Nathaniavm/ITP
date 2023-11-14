package ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.junit.jupiter.api.TestInstance;

/*
 * Unit test for BankAppController
*/
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAppControllerTest extends ApplicationTest {
  private BankAppController controller;
  private Parent root;

  @DisplayName("Deleting a profile")
  private void deleteTestProfile() {
    clickOn("#profileTab");
    clickOn("#settingsButton");
    clickOn("#deleteProfileButton");
  }

  @DisplayName("Making a card that can be tested on")
  private void makeTestCard(String accountName) {
    clickOn("#profileTab");
    clickOn("#cardsButton");
    clickOn("#orderCardButton");
    clickOn("#orderOrBlockChoiceBox");
    clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals(accountName))
        .findAny().get().getAccNr());
    clickOn("#orderOrBlockButton");
  }

  @DisplayName("Making an account that is tested on")
  private void makeTestAccount(String accountName, String accountType) {
    clickOn("#newAccountButton");
    clickOn("#selectAccountType");
    clickOn(accountType);
    clickOn("#giveAccountName").write(accountName);
    clickOn("#createAccountButton");
    assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals(accountName))
        .findAny().isPresent());
  }

  @Override
  @DisplayName("Loading the stage")
  public void start(Stage stage) throws IOException {
    controller = new BankAppController();
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
    root = fxmlLoader.load();
    stage.setScene(new Scene(root));
    stage.show();
  }

  private boolean setupRun = false;

  @BeforeEach
  void setup() {
    if (!setupRun) {
      clickOn("#signUpButton");
      clickOn("#fullName").write("Ada Lovelace");
      clickOn("#email").write("adalovelace@gmail.no");
      clickOn("#phoneNr").write("98987765");
      clickOn("#password").write("koding1234");
      clickOn("#passwordConfirm").write("koding1234");
      clickOn("#registerButton");

      clickOn("#newAccountButton");
      // Assuming #selectAccountType is a ChoiceBox
      ChoiceBox<String> choiceBox = lookup("#selectAccountType").query();

      // Interact with the ChoiceBox to open the dropdown
      clickOn(choiceBox);

      // Select the desired item (e.g., "Checking account")
      clickOn("Checking account");
      clickOn("#giveAccountName").write("BlockCard TestAccount");
      clickOn("#createAccountButton");

      // Assuming #selectAccountType is a ChoiceBox
      ChoiceBox<String> choiceBox1 = lookup("#selectAccountType").query();

      // Interact with the ChoiceBox to open the dropdown
      clickOn(choiceBox1);

      // Select the desired item (e.g., "Checking account")
      clickOn("Checking account");
      clickOn("#giveAccountName").write("Transfer TestSpendingAccount");
      clickOn("#createAccountButton");

      clickOn("#selectAccountType");
      clickOn("Savings account");
      clickOn("#giveAccountName").write("Transfer TestSavingsAccount");
      clickOn("#createAccountButton");

      clickOn("#giveAccountName").write("Delete TestAccount");
      clickOn("#createAccountButton");

      clickOn("#selectAccountType");
      clickOn("BSU");
      clickOn("#giveAccountName").write("Transfer TestBSUAccount");
      clickOn("#createAccountButton");

      clickOn("#profileTab");
      clickOn("#cardsButton");
      clickOn("#orderCardButton");
      clickOn("#orderOrBlockChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("BlockCard TestAccount"))
          .findAny().get().getAccNr());
      clickOn("#orderOrBlockButton");
      setupRun = true;
    }
  }

  // private boolean madeCard1 = false;
  // private boolean madeCard2 = false;
  // private boolean blockedCard2 = false;

  // @BeforeEach
  // void setupCard1() {
  // if (!madeCard1) {
  // clickOn("#emailInput").write("adalovelace@gmail.no");
  // clickOn("#passwordInput").write("koding1234");
  // clickOn("#loginButton");
  // makeTestAccount("BlockCard TestAccount", "Checking account");
  // makeTestCard("BlockCard TestAccount");
  // madeCard1 = true;
  // }
  // }

  // @BeforeEach
  // void setupCard2() {
  // if (!madeCard2) {
  // clickOn("#emailInput").write("adalovelace@gmail.no");
  // clickOn("#passwordInput").write("koding1234");
  // clickOn("#loginButton");
  // makeTestAccount("UnblockCard TestAccount", "Checking account");
  // makeTestCard("UnblockCard TestAccount");
  // madeCard2 = true;
  // }
  // }

  // @BeforeEach
  // void setupBlockCard2() {
  // if (!blockedCard2) {
  // clickOn("#emailInput").write("adalovelace@gmail.no");
  // clickOn("#passwordInput").write("koding1234");
  // clickOn("#loginButton");

  // clickOn("#profileTab");
  // clickOn("#cardsButton");
  // clickOn("#blockCardButton");
  // clickOn("#orderOrBlockChoiceBox");
  // clickOn(
  // controller.getProfile().getAccounts().stream().filter(a ->
  // a.getName().equals("UnblockCard TestAccount"))
  // .findAny().get().getAccNr());
  // clickOn("#orderOrBlockButton");
  // blockedCard2 = true;
  // }
  // }

  @Nested
  public class testMainPages {
    private int count = 0;

    @BeforeEach
    @DisplayName("Logging in to the Ada Lovelace profile")
    private void loginSetUp() {
      clickOn("#emailInput").write("adalovelace@gmail.no");
      clickOn("#passwordInput").write("koding1234");
      clickOn("#loginButton");
    }

    @AfterEach
    public void testDeleteProfile() {
      count++;
      if (count > 12) {
        clickOn("#deleteProfileButton");
        FxAssert.verifyThat("#login", NodeMatchers.isVisible());
      }
    }

    @Test
    @DisplayName("Testing if the spending page is loaded correctly")
    public void testSpendingTab() {
      clickOn("#spendingTab");
      FxAssert.verifyThat("#spending", NodeMatchers.isVisible());
      FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
    }

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

    @Test
    @DisplayName("Testing if the savings page is loaded correctly")
    public void testSavingsTab() {
      clickOn("#savingsTab");
      FxAssert.verifyThat("#savings", NodeMatchers.isVisible());

      FxAssert.verifyThat("#totalBalance", hasText("100"));
      FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
    }

    @Test
    @DisplayName("Testing if the profile page is loaded correctly")
    public void testProfileTab() {
      clickOn("#profileTab");
      FxAssert.verifyThat("#profile", NodeMatchers.isVisible());
    }

    @Test
    @DisplayName("Testing if a new account is made correctly when the input is valid, and not made it the inputs are unvalid")
    public void testMakeAccount() {
      clickOn("#newAccountButton");
      FxAssert.verifyThat("#newAccount", NodeMatchers.isVisible());
      // Assuming #selectAccountType is a ChoiceBox
      ChoiceBox<String> choiceBox = lookup("#selectAccountType").query();

      // Interact with the ChoiceBox to open the dropdown
      clickOn(choiceBox);

      // Select the desired item (e.g., "Checking account")
      clickOn("Checking account");
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

    @Test
    @DisplayName("Testing if an account is deleted correctly for already exsisting account, and that you get an error if you try to delete an account that doesn't exist")
    public void testDeleteAccount() {
      clickOn("#deleteAccountButton");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
      clickOn("#deleteAccountName").write("Delete TestAccount");
      clickOn("#deleteAccountNow");
      assertFalse(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Delete TestAccount"))
          .findAny().isPresent());
      FxAssert.verifyThat("#overview", NodeMatchers.isVisible());

      clickOn("#deleteAccountButton");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
      clickOn("#deleteAccountName").write("Delete TestAccount");
      clickOn("#deleteAccountNow");
      FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
    }

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
          .filter(a -> a.getName().equals("Transfer TestSpendingAccount")).findFirst().get().getAccNr());
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
          .filter(a -> a.getName().equals("Transfer TestSpendingAccount")).findFirst().get().getAccNr());
      clickOn("#transferToChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Transfer TestSavingsAccount")).findFirst().get().getAccNr());
      clickOn("#transferButton");
      assertTrue(
          controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Transfer TestSavingsAccount"))
              .findFirst().get().getBalance() == 50);
      assertTrue(
          controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Transfer TestSpendingAccount"))
              .findFirst().get().getBalance() == 0);

      clickOn("#transferAmount").write("50");
      clickOn("#transferFromChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Transfer TestSavingsAccount")).findFirst().get().getAccNr());
      clickOn("#transferToChoiceBox");
      clickOn(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Transfer TestBSUAccount")).findFirst().get().getAccNr());
      clickOn("#transferButton");
      assertTrue(controller.getProfile().getAccounts().stream()
          .filter(a -> a.getName().equals("Transfer TestBSUAccount")).findFirst()
          .get().getBalance() == 50);
      assertTrue(
          controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Transfer TestSavingsAccount"))
              .findFirst().get().getBalance() == 0);
    }

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
    @DisplayName("Test the different card functions")
    public class testCards {
      @BeforeEach
      @DisplayName("Loads the page where one can make and blaock cards")
      public void cardSetUp() {
        clickOn("#profileTab");
        clickOn("#cardsButton");
      }

      @Test
      @DisplayName("Testing if a card can be made")
      public void testMakeCard() {
        clickOn("#profileTab");
        clickOn("#cardsButton");
        FxAssert.verifyThat("#settings", NodeMatchers.isVisible());
        int tempCardSize = controller.getProfile().getBankCards().size();

        clickOn("#orderCardButton");
        FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
        clickOn("#orderOrBlockChoiceBox");
        clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Spendings account"))
            .findAny().get().getAccNr());
        clickOn("#orderOrBlockButton");
        assertTrue(controller.getProfile().getBankCards().size() > tempCardSize);
      }

      @Test
      @DisplayName("Testing if a card can be blocked")
      public void testBlockCard() {
        clickOn("#profileTab");
        clickOn("#cardsButton");
        clickOn("#blockCardButton");
        FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
        clickOn("#orderOrBlockChoiceBox");
        clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("BlockCard TestAccount"))
            .findAny().get().getAccNr());
        clickOn("#orderOrBlockButton");
        assertTrue(!controller.getProfile().getBankCards().stream()
            .filter(a -> a.getAccount().getName().equals("BlockCard TestAccount")).filter(c -> c.isCardBlocked())
            .findAny().isEmpty());
      }

      // @Test
      // @DisplayName("Testing if a card can be unblocked")
      // public void testUnblockCard() {
      // clickOn("#profileTab");
      // clickOn("#cardsButton");
      // clickOn("#unblockCardButton");
      // System.out.println("har vært inne i unblock card");
      // FxAssert.verifyThat("#deleteAccount", NodeMatchers.isVisible());
      // clickOn("#orderOrBlockChoiceBox");
      // clickOn(controller.getProfile().getAccounts().stream().filter(a ->
      // a.getName().equals("Spendings account"))
      // .findAny().get().getAccNr());
      // clickOn("#orderOrBlockButton");
      // assertTrue(controller.getProfile().getBankCards().stream()
      // .filter(a -> a.getAccount().getName().equals("UnblockCard
      // TestAccount")).filter(c -> c.isCardBlocked())
      // .findAny().isEmpty());
      // }

    }

    @Nested
    @DisplayName("Test the different bill functions")
    public class testBill {
      @BeforeEach
      @DisplayName("Loads the payment tab")
      public void billSetUp() {
        clickOn("#paymentsTab");
      }

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
    }
  }

  // @Test
  // @DisplayName("Testing if a profile gets made accordingly")
  // public void testHandleSignUpClick() {
  // clickOn("#signUpButton");
  // FxAssert.verifyThat("#register", NodeMatchers.isVisible());
  // clickOn("#fullName").write("Elon Musk");
  // clickOn("#email").write("elon@gmail.no");
  // clickOn("#phoneNr").write("98887765");
  // clickOn("#password").write("teknologi1234");
  // clickOn("#passwordConfirm").write("teknologi1234");
  // clickOn("#registerButton");

  // assertTrue(controller.getProfile().getName().equals("Elon Musk"));
  // assertTrue(controller.getProfile().getEmail().equals("elon@gmail.no"));
  // assertTrue(controller.getProfile().getTlf().equals("98887765"));
  // assertTrue(controller.getProfile().getPassword().equals("teknologi1234"));

  // deleteTestProfile();

  // }

  // @Test
  // @DisplayName("Testing if the back arrow works")
  // public void testHandleBackArrow() {
  // clickOn("#signUpButton");
  // clickOn("#backArrow");
  // FxAssert.verifyThat("#login", NodeMatchers.isVisible());
  // }

}