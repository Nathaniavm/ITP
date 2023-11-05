package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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

import core.Profile;
import core.Accounts.AbstractAccount;
import core.Accounts.SpendingsAccount;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

/*
 * Unit test for BankAppController
*/
public class BankAppControllerTest extends ApplicationTest {
    private BankAppController controller;
    private Parent root;
    Profile NTNU;

    private void setUp() {
        NTNU = new Profile("NTNU Gløshaugen", "NTNU@ntnu.no", "98989898", "Administrator59");
        NTNU.addAccount(new SpendingsAccount("NTNU", NTNU));
    }

    @Override
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

        @Test
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

        @Test
        public void testSavingsAccount() {
            // kanke paye bills, og kanke ha bankkort

        }

        @Test
        public void testBSUaccount() {
            // kanke paye bills, og kanke ha bankkort

        }

        @Test
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

        @Test
        public void testPayMoney() {
            clickOn("#paymentsTab");
            clickOn("#goToPayButton");
            FxAssert.verifyThat("#pay", NodeMatchers.isVisible());

            clickOn("#payAmount").write("20");
            clickOn("#payFromChoiceBox");
            clickOn(controller.getProfile().getAccounts().stream().filter(a -> a.getName().equals("Spendings account"))
                    .findAny().get().getAccNr());
            clickOn("#payTo").write(NTNU.getAccounts().get(0).getAccNr());
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
        public class testBill {
            @BeforeEach
            public void billSetUp() {
                clickOn("#paymentsTab");
            }

            @Test
            public void testMakeBill() {
                clickOn("#newBillButton");
                FxAssert.verifyThat("#pay", NodeMatchers.isVisible());

                clickOn("#billName").write("leie");
                clickOn("#billAmount").write("10");
                clickOn("#payerAccountChoiceBox");
                clickOn(controller.getProfile().getAccounts().stream()
                        .filter(a -> a.getName().equals("Spendings account")).findAny().get().getAccNr());
                clickOn("#sellerAccount").write(NTNU.getAccounts().get(0).getAccNr());
                clickOn("#setNewBillButton");

                assertTrue(controller.getProfile().getAccounts().stream()
                        .filter(a -> a.getName().equals("Spendings account")).findAny().get().getBalance() == 20);
                assertTrue(controller.getProfile().getBills().stream().filter(a -> a.getBillName().equals("leie"))
                        .findAny().get().getPayerAccount().equals(controller.getProfile().getAccounts().stream()
                                .filter(a -> a.getName().equals("Spendings account")).findAny().get()));
                assertTrue(controller.getProfile().getBills().stream().filter(a -> a.getBillName().equals("leie"))
                        .findAny().get().getAmount() == 10);
            }

            // @Test
            // public void testPayBill() {

            // }
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
                FxAssert.verifyThat("#settings", NodeMatchers.isVisible());
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
                clickOn("#changeNumberTo").write("47765");
                clickOn("#updateSettings");
                assertFalse(controller.getProfile().getTlf().equals("47765"));

                clickOn("#profileTab");
                clickOn("#settingsButton");
                clickOn("#changeNumberTo").write("98987765");
                clickOn("#changeEmailTo").write("adalovelace@gmail.no");
                clickOn("#changePasswordTo").write("koding1234");
                clickOn("#confirmChangePassword").write("koding1234");
                clickOn("#updateSettings");
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