package ui;

import java.io.IOException;

import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class AppController {

    @FXML
    private AnchorPane header;

    @FXML
    private Label profileName;

    @FXML
    private Label moneyLeft;

    @FXML
    private AnchorPane body;

    @FXML
    private GridPane accountsTable;

    @FXML
    private Label spendingAccount;

    @FXML
    private Label savingAccount;

    @FXML
    private Label bsu;

    @FXML
    private AnchorPane spendingTab;

    @FXML
    private AnchorPane paymentsTab;

    @FXML
    private AnchorPane homeTab;

    @FXML
    private AnchorPane savingsTab;

    @FXML
    private AnchorPane profileTab;

    @FXML
    private Button button;

    @FXML
    private void initializeProfile() {
        profileTab.setOnMouseClicked(event -> {
            try {
                getOnActionProfile(event);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void getOnActionProfile(MouseEvent event) throws IOException {
        Stage primaryStage = (Stage) profileTab.getScene().getWindow();
        // String tab = ((Label) profileTab.getChildren().get(0)).getText(); Hente ut
        // text verdi til tabben man trykker på

        primaryStage.setTitle("Bankapp - Profile");

        FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("Profile.fxml"));
        Parent tabPane = tabLoader.load();
        Scene tabScene = new Scene(tabPane);

        primaryStage.setScene(tabScene);
        primaryStage.show();

    }

}
