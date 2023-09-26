package ui;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * Unit test for BankApp
 */
public class BankAppTest extends ApplicationTest {
    private BankAppController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Overview.fxml"));
        root = fxmlLoader.load();
        this.controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    private void testController_initial() {
        assertNotNull(this.controller);
    }

    @Test
    private void testStage_initial() {
        assertNotNull(this.stage);
    }

    @Test
    public void testBankSettings() {
        clickOn("#profileTab");
        WaitForAsyncUtils.waitForFxEvents();
        assertNotNull(findSceneRootWithId("profile"));
    }

    private Parent findSceneRootWithId(String id) {
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage && window.isShowing()) {
                Parent root = window.getScene().getRoot();
                if (id.equals(root.getId())) {
                    return root;
                }
            }
        }
        return null;
    }
}
