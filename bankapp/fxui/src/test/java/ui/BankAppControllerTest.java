package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;

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
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
        root = fxmlLoader.load();
        stage.setScene(new Scene(root));
        this.controller = controller;
        stage.show();
    }

    @Nested
    public class testRegisterButtons {
        @BeforeEach
        private void setUp() {
            clickOn("#signUpButton");
        }

        @Test
        public void testHandleSignUpClick() {
            FxAssert.verifyThat("#register", NodeMatchers.isVisible());
        }

        @Test
        public void testHandleBackArrow() {
            clickOn("#backArrow");
            FxAssert.verifyThat("#login", NodeMatchers.isVisible());
        }
    }

    @Nested
    public class testMainPages {
        @BeforeEach
        private void setUp() {
            clickOn("#loginButton");
        }

        @Test
        public void testSpendingTab() {
            clickOn("#spendingTab");
            FxAssert.verifyThat("#spending", NodeMatchers.isVisible());
        }

        @Test
        public void testPaymentTab() {
            clickOn("#paymentsTab");
            FxAssert.verifyThat("#payments", NodeMatchers.isVisible());
        }

        @Test
        public void testSavingsTab() {
            clickOn("#savingsTab");
            FxAssert.verifyThat("#savings", NodeMatchers.isVisible());
        }

        @Test
        public void testProfileTab() {
            clickOn("#profileTab");
            FxAssert.verifyThat("#profile", NodeMatchers.isVisible());
        }
    }

    // @Test
    // public void testLogin() {
    //     clickOn("#loginButton");
    //     FxAssert.verifyThat("#overview", NodeMatchers.isVisible());
    // }
}