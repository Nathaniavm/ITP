package ui;

import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

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
    private void initializePayments() {
        paymentsTab.setOnMouseClicked(this::getOnActionPayments);
    }

    @FXML
    private void getOnActionPayments(MouseEvent event) {

    }

}
