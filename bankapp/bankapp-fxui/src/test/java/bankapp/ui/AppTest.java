package bankapp.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

/**
 * Unit test for App
 */
public class AppTest extends ApplicationTest {
    private AppController controller;
    private Parent root;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Overview.fxml"));
        root = fxmlLoader.load();
        controller = fxmlLoader.getController();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public Parent getRootNode() {
        return root;
    }

    @Test
    public void testButtonClick() {
        // Finn knappen med en unik ID eller tekst
        Button yourButton = lookup("#profileTab").query();

        // Klikk på knappen
        clickOn(yourButton);

        // Utfør asserter eller sjekker basert på hva som skal skje når knappen klikkes
    }
}
