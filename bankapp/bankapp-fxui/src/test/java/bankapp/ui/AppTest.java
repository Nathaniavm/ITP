package bankapp.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import ui.AppController;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.service.query.NodeQuery;

/**
 * Unit test for App
 */
public class AppTest extends ApplicationTest {
    private AppController controller;
    private Parent root;
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(
                this.getClass().getResource("/bankapp/bankapp-fxui/src/main/resources/ui/Overview.fxml"));
        root = fxmlLoader.load();
        // controller = fxmlLoader.getController();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @Test
    public void testButtonClick() {
        // AnchorPane profileTab = (AnchorPane) scene.lookup("#profileTab");
        clickOn(LabeledMatchers.hasText("HJELP"));
        // assertEquals(this.stage.getScene().getRoot().getId(), "HJELP");

        ;
    }
}
