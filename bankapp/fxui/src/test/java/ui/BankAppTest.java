package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Unit test for BankApp
 */
public class BankAppTest extends ApplicationTest {
  private BankAppController controller;
  private Parent root;
  private Stage stage;

  @Test
  public void testStartMethod() {
    Platform.runLater(() -> {
      try {
        BankApp app = new BankApp();
        Stage stage = new Stage();
        app.start(stage);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  @Override
  public void start(Stage stage) throws IOException {
    this.stage = stage;
    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("Login.fxml"));
    root = fxmlLoader.load();
    this.controller = fxmlLoader.getController();
    stage.setScene(new Scene(root));
    stage.show();
  }

  @Test
  public void testController_initial() {
    assertNotNull(this.controller);
  }

  @Test
  public void testStage_initial() {
    assertNotNull(this.stage);
    assertEquals(this.stage.getScene().getRoot().getId(), "login");
  }

}
