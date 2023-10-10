package ui;

import java.io.IOException;

import core.Account;
import core.Profile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Labeled;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class BankAppController {

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
    private Button loginButton;

    @FXML 
    private TextField emailInput;

    @FXML 
    private TextField passwordInput;

    @FXML 
    private Text signUpButton;

    @FXML 
    private ImageView backArrow;

    private static Profile profile;

    public void initialize() {
        if (accountsTable != null && profile == null) {
            profile = new Profile("james heui", "james@gmail.com", "12345678", "passord12");
            profile.createAccount("Spendings account");
            profile.createAccount("Savings account");
            profile.createAccount("BSU");
            updateAccounts();
        } else if (accountsTable != null) {
            updateAccounts();
        }
        if (profileName != null) {
            profileName.setText(profile.getName() + "'s Profile");
        }
    }

    @FXML
    public void initializeTab(MouseEvent event) throws IOException {
        String tab = ((Label) ((AnchorPane) event.getSource()).getChildren().get(0)).getText();
        if (tab.equals("Home")) {
            tab = "Overview";
        }
        switchTab(tab);

    }

    @FXML
    private void switchTab(String tab) throws IOException {
        Stage primaryStage = (Stage) profileTab.getScene().getWindow();

        primaryStage.setTitle("Bankapp - " + tab);

        FXMLLoader tabLoader = new FXMLLoader(getClass().getResource(tab + ".fxml"));
        Parent tabPane = tabLoader.load();
        Scene tabScene = new Scene(tabPane);

        primaryStage.setScene(tabScene);
        primaryStage.show();
    }

    @FXML
    public void updateAccounts() {
        int count = 0;
        System.out.println(profile.getAccounts());
        for (Account account : profile.getAccounts()) {
            Label accountName = new Label(account.getName());
            Label accountBalance = new Label(String.valueOf(account.getBalance()));
            if (count == 0) {
                accountsTable.add(accountName, 0, count);
                accountsTable.add(accountBalance, 1, count);

            } else {
                System.out.println(count);
                accountsTable.addRow(count);
                accountsTable.add(accountName, 0, count);
                accountsTable.add(accountBalance, 1, count);
            }
            count += 1;
        }
    }

    @FXML
    private void handleSignUpClick(MouseEvent event) {
        // Handle the click event here
        // Load the "Register.fxml" file and navigate to it
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Register.fxml"));
            Parent root = loader.load();

            // Create a new scene
            Scene scene = new Scene(root);

            // Get the stage from the event source (the Text element)
            Stage stage = (Stage) signUpButton.getScene().getWindow();

            // Set the new scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleBackArrow(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) backArrow.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginButton(MouseEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Overview.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
