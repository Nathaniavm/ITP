package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

import core.Accounts.AbstractAccount;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/*
 * Unit test for BankAppController
*/
public class BankAppControllerTest extends ApplicationTest {
    private BankAppController controller;
    private Parent root;

    @Override
    public void start(Stage stage) throws IOException {
        controller = new BankAppController();
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Nested
    public class testMainPages {
        @BeforeEach
        private void loginSetUp() {
            clickOn("#emailInput").write("adalovelace@gmail.no");
            clickOn("#passwordInput").write("koding1234");
            clickOn("#loginButton");
        }

        @Test
        public void testSpendingTab() {
            clickOn("#spendingTab");
            FxAssert.verifyThat("#spending", NodeMatchers.isVisible());
            FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
        }

        @Test
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
        public void testSavingsTab() {
            clickOn("#savingsTab");
            FxAssert.verifyThat("#savings", NodeMatchers.isVisible());

            FxAssert.verifyThat("#totalBalance", hasText("100"));
            FxAssert.verifyThat("#profileName", hasText("Ada Lovelace's Profile"));
        }

        @Test
        public void testProfileTab() {
            clickOn("#profileTab");
            FxAssert.verifyThat("#profile", NodeMatchers.isVisible());
        }

        @Test
        public void testMakeAccount() {
            clickOn("#newAccountButton");
            clickOn("#giveAccountName").write("Brukerkonto2");
            clickOn("#createAccountButton");
            assertTrue(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Brukerkonto2"))
                    .findAny().isPresent());
        }

        @Test
        public void testTransferMoney() {
            clickOn("#paymentsTab");
            clickOn("#goToTransferButton");
            clickOn("#transferAmount").write("50");
            clickOn("#transferFromAccount").write(controller.getProfile().getAccounts().stream()
                    .filter(a -> a.getName().equals("Spendings account")).findFirst().get().getAccNr());
            clickOn("#transferToAccount").write(controller.getProfile().getAccounts().stream()
                    .filter(a -> a.getName().equals("Brukerkonto2")).findFirst().get().getAccNr());
            clickOn("#transferButton");

            clickOn("#homeTab");
            FxAssert.verifyThat("#spendingAccountBalance", hasText("50"));

            clickOn("#savingsTab");
            FxAssert.verifyThat("#totalBalance", hasText("100"));
        }

        @Nested
        public class testProfile {
            @BeforeEach
            private void profileSetUp() {
                clickOn("#profileTab");
                clickOn("#settingsButton");
            }

            @Test
            public void testProfileChange() {
                FxAssert.verifyThat("#profile", NodeMatchers.isVisible());
                clickOn("#changeNumberTo").write("45456677");
                clickOn("#changeEmailTo").write("al@gmail.no");
                clickOn("#changePasswordTo").write("koding4321");
                clickOn("#confirmChangePassword").write("koding4321");
                clickOn("#updateSettings");

                assertTrue(controller.getProfile().getTlf().equals("45456677"));
                assertTrue(controller.getProfile().getEmail().equals("al@gmail.no"));
                assertTrue(controller.getProfile().getPassword().equals("koding4321"));

                clickOn("#profileTab");
                clickOn("#settingsButton");
                clickOn("#changeNumberTo").write("98987765");
                clickOn("#changeEmailTo").write("adalovelace@gmail.no");
                clickOn("#changePasswordTo").write("koding1234");
                clickOn("#confirmChangePassword").write("koding1234");
            }

            @Test
            public void testDeleteProfile() {
                clickOn("#deleteProfileButton");
                FxAssert.verifyThat("#login", NodeMatchers.isVisible());
            }
        }
    }

    @Nested
    public class testRegisterButtons {
        @BeforeEach
        private void registerSetUp() {
            clickOn("#signUpButton");
        }

        @Test
        public void testHandleSignUpClick() {
            FxAssert.verifyThat("#register", NodeMatchers.isVisible());
            clickOn("#fullName").write("Ada Lovelace");
            clickOn("#email").write("adalovelace@gmail.no");
            clickOn("#phoneNr").write("98987765");
            clickOn("#password").write("koding1234");
            clickOn("#passwordConfirm").write("koding1234");

            clickOn("#registerButton");
        }

        @Test
        public void testHandleBackArrow() {
            clickOn("#backArrow");
            FxAssert.verifyThat("#login", NodeMatchers.isVisible());
        }
    }
}