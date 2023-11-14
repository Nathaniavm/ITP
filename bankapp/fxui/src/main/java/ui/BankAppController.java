package ui;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import core.BankCard;
import core.Logics;
import core.Profile;
import core.Transaction;
import core.accounts.AbstractAccount;
import core.accounts.BsuAccount;
import core.accounts.SavingsAccount;
import core.accounts.SpendingsAccount;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

public class BankAppController {
  // almost all fxmls
  // almost all fxmls
  @FXML
  private Label profileName;

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

  // register fxml

  @FXML
  private ImageView backArrow;

  @FXML
  private TextField fullName;

  @FXML
  private TextField email;

  @FXML
  private TextField phoneNr;

  @FXML
  private PasswordField password;

  @FXML
  private PasswordField passwordConfirm;

  @FXML
  private Button registerButton;

  @FXML
  private Label registerError;

  // login FXML
  @FXML
  private Button loginButton;

  @FXML
  private TextField emailInput;

  @FXML
  private PasswordField passwordInput;

  @FXML
  private Label loginError, signUpButton;

  // pay FXML
  @FXML
  private ChoiceBox<String> payFromChoiceBox;

  @FXML
  private TextField payTo, payAmount;

  @FXML
  private AnchorPane payButton;

  @FXML
  private Text feedbackInPay;

  // payments FXML
  @FXML
  private AnchorPane goToPayButton, goToTransferButton;

  // transfer FXML

  @FXML
  private TextField transferAmount;

  @FXML
  private AnchorPane transferButton;

  @FXML
  private Text feedbackInTransfer;

  @FXML
  private ChoiceBox<String> transferFromChoiceBox, transferToChoiceBox;

  // savings fxml
  @FXML
  private Label transferSavingsButton, newSavingAccountButton, totalBalance;

  // overview fxml
  @FXML
  private Label spendingAccountBalance, newAccountButton, deleteAccountButton;

  @FXML
  private GridPane accountsTable;

  @FXML
  private AnchorPane overview;

  // spending fxml
  @FXML
  private GridPane transactionTable;

  // newAccount fxml

  @FXML
  private ChoiceBox<String> selectAccountType;

  @FXML
  private TextField giveAccountName;

  @FXML
  private AnchorPane createAccountButton;

  @FXML
  private Text feedbackInNewAccount;

  // settings fxml

  @FXML
  private TextField changeNumberTo, changePasswordTo, confirmChangePassword;

  @FXML
  private AnchorPane updateSettings;

  @FXML
  private Text feedbackInSettings;

  @FXML
  private Label deleteProfileButton;

  // profile fxml

  @FXML
  private AnchorPane settingsButton, cardsButton, logOutButton;

  // deleteAccount fxml

  @FXML
  private TextField deleteAccountName;

  @FXML
  private Button deleteAccountNow;

  @FXML
  private AnchorPane deleteAccount;

  @FXML
  private Text feedbackInDeleteAccount;

  // cards fxml
  @FXML
  private Label noCardsLabel, orderCardButton, blockCardButton, unBlockCardButton, accountsWithBankcardLabel;

  @FXML
  private GridPane cardsTable;

  // orderOrBlock fxml
  @FXML
  private Text orderOrBlockTitle, feedbackInOrderOrBlock;

  @FXML
  private ChoiceBox<String> orderOrBlockChoiceBox;

  @FXML
  private Button orderOrBlockButton;

  private static Profile profile;

  private static final String endpointBaseUri = "http://localhost:8080/profiles/";
  private static RemoteProfilesAccess profilesAccess;

  @FXML
  public void updateCards() {
    if (!profile.getBankCards().isEmpty()) {
      System.out.println(profile.getBankCards());
      noCardsLabel.setVisible(false);
      accountsWithBankcardLabel.setVisible(true);
      int count = 0;
      String cardBlocked = "   (Card is blocked)";
      for (BankCard bankCard : profile.getBankCards()) {
        String message;
        if (bankCard.isCardBlocked()) {
          message = cardBlocked;
        } else {
          message = "";
        }
        Label label = new Label(
            "AccNr: " + bankCard.getAccount().getAccNr() + "  CardNr: " + bankCard.getCardNr() + message);
        label.setLayoutX(-20);
        label.setStyle("-fx-font-size: 12px; -fx-min-width: 100px; -fx-min-height: 30px;");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefWidth(400.0);
        anchorPane.setPrefHeight(300.0);
        anchorPane.getChildren().add(label);
        AnchorPane.setTopAnchor(anchorPane, count * (30.0));
        cardsTable.add(anchorPane, 0, count);
        AnchorPane.setTopAnchor(cardsTable, 100.0);
        count++;
      }
    } else {
      noCardsLabel.setVisible(true);
      accountsWithBankcardLabel.setVisible(false);
    }
  }

  /**
   * sets the stage for ordering a card
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void handleOrderANewCardStage1(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderOrBlockCard.fxml"));
    AnchorPane orderOrBlockCard = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(orderOrBlockCard));
    stage.setTitle("Order A new Card");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();
    Parent root = stage.getScene().getRoot();
    Text orderOrBlockTitle = (Text) root.lookup("#orderOrBlockTitle");
    Button orderOrBlockButton = (Button) root.lookup("#orderOrBlockButton");
    ChoiceBox<String> orderOrBlockChoiceBox = (ChoiceBox<String>) root.lookup("#orderOrBlockChoiceBox");
    Text feedbackInOrderOrBlock = (Text) root.lookup("#feedbackInOrderOrBlock");

    if (orderOrBlockTitle != null) {
      orderOrBlockTitle.setText("Order Card");
      orderOrBlockTitle.setText("Order Card");
    }
    if (orderOrBlockButton != null) {
      orderOrBlockButton.setText("Order");
      orderOrBlockButton.setText("Order");
    }

    if (profile.accountsWithoutBankcards().size() == 0) {
      feedbackInOrderOrBlock.setText("No accounts that \n can be bankcards");
      feedbackInOrderOrBlock.setFill(Color.RED);
    }

    else if (profile.getAccounts().size() != 0 && orderOrBlockChoiceBox != null) {
      orderOrBlockChoiceBox.getItems().addAll(profile.accountsWithoutBankcards());
    }

  }

  /**
   * sets the stage for blocking a card
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void handleBlockCardStage1(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderOrBlockCard.fxml"));
    AnchorPane orderOrBlockCard = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(orderOrBlockCard));
    stage.setTitle("Block Card");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();

    Parent root = stage.getScene().getRoot();
    Text orderOrBlockTitle = (Text) root.lookup("#orderOrBlockTitle");
    Button orderOrBlockButton = (Button) root.lookup("#orderOrBlockButton");
    ChoiceBox<String> orderOrBlockChoiceBox = (ChoiceBox<String>) root.lookup("#orderOrBlockChoiceBox");
    Text feedbackInOrderOrBlock = (Text) root.lookup("#feedbackInOrderOrBlock");

    if (orderOrBlockTitle != null) {
      orderOrBlockTitle.setText("Block Card");
    }
    if (orderOrBlockButton != null) {
      orderOrBlockButton.setText("Block");
    }

    if (profile.getBankCards().isEmpty()) {
      feedbackInOrderOrBlock.setText("You have no bankcards \n to block");
      feedbackInOrderOrBlock.setFill(Color.RED);
    }
    try {
      orderOrBlockChoiceBox.getItems().addAll(profile.getListOfNotBlockedAccNrBankCards());

    } catch (Exception e) {
      feedbackInOrderOrBlock.setText("Something went wrong");
      feedbackInOrderOrBlock.setFill(Color.RED);
    }
  }

  /**
   * sets the stage for unblocking a card
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void handleUnblockCardStage1(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderOrBlockCard.fxml"));
    AnchorPane unblockCardAnchorPane = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(unblockCardAnchorPane));
    stage.setTitle("Unblock Card");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();

    Parent root = stage.getScene().getRoot();
    Text orderOrBlockTitle = (Text) root.lookup("#orderOrBlockTitle");
    Button orderOrBlockButton = (Button) root.lookup("#orderOrBlockButton");
    ChoiceBox<String> orderOrBlockChoiceBox = (ChoiceBox<String>) root.lookup("#orderOrBlockChoiceBox");
    Text feedbackInOrderOrBlock = (Text) root.lookup("#feedbackInOrderOrBlock");

    if (orderOrBlockTitle != null) {
      orderOrBlockTitle.setText("Unblock Card");
    }
    if (orderOrBlockButton != null) {
      orderOrBlockButton.setText("Unblock");
    }

    if (profile.getListOfBlockedAccNrBankCards().size() == 0) {
      feedbackInOrderOrBlock.setText("You have no bankcards \n to unblock");
      feedbackInOrderOrBlock.setFill(Color.RED);
    }

    try {
      orderOrBlockChoiceBox.getItems().addAll(profile.getListOfBlockedAccNrBankCards());
    } catch (Exception e) {
      feedbackInOrderOrBlock.setText("Something went wrong");
      feedbackInOrderOrBlock.setFill(Color.RED);
    }
  }

  /**
   * handling the actual process of ordering or blocking or unblocking
   * 
   * @param event
   * @throws StreamWriteException
   * @throws DatabindException
   * @throws IOException
   */
  @FXML
  public void handleOrderOrBlockStage2(MouseEvent event) throws StreamWriteException, DatabindException, IOException {
    String accNr = orderOrBlockChoiceBox.getValue();

    // createBankCard
    if (orderOrBlockTitle.getText().equals("Order Card")) {
      SpendingsAccount spendingsAccount = null;
      spendingsAccount = profile.findSpendingsAccount(accNr);

      try {
        spendingsAccount.createBankCard();
        orderOrBlockChoiceBox.setValue("");
        feedbackInOrderOrBlock.setText("Order completed!");
        writeInfo();
      } catch (Exception e) {
        feedbackInOrderOrBlock.setText("Something went wrong");
        feedbackInOrderOrBlock.setFill(Color.RED);
      }

    } else if (orderOrBlockTitle.getText().equals("Block Card")) {
      try {
        profile.getBankCard(accNr).blockCard();
        orderOrBlockChoiceBox.setValue("");
        feedbackInOrderOrBlock.setText("Block completed!");
        writeInfo();
      } catch (Exception e) {
        feedbackInOrderOrBlock.setText(e.getMessage());
        feedbackInOrderOrBlock.setFill(Color.RED);
      }

    }

    else if (orderOrBlockTitle.getText().equals("Unblock Card")) {
      // do unblock stuff

      try {
        BankCard bankCard = profile.getBankCard(accNr);
        bankCard.unblockCard();
        orderOrBlockChoiceBox.setValue("");
        feedbackInOrderOrBlock.setText("Unblock completed!");
        writeInfo();
      } catch (Exception e) {
        feedbackInOrderOrBlock.setText(e.getMessage());
        feedbackInOrderOrBlock.setFill(Color.RED);
      }

    }
  }

  /**
   * Initializes fields based on current page
   * 
   */
  public void initialize() {
    try {
      profilesAccess = new RemoteProfilesAccess(new URI(endpointBaseUri));
    } catch (URISyntaxException e) {
      System.out.println(e);
    }
    if (accountsTable != null && profile == null) {
      updateAccounts();
    } else if (accountsTable != null) {
      updateAccounts();
    }
    if (transactionTable != null && profile == null) {
      try {
        updateTransaction();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (transactionTable != null) {
      try {
        updateTransaction();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    if (profileName != null) {
      profileName.setText(profile.getName() + "'s Profile");
    }
    if (totalBalance != null) {
      totalBalance.setText(String.valueOf(profile.getTotalBalance()));
    }
    if (spendingAccountBalance != null && !profile.getAccounts().isEmpty()) {
      spendingAccountBalance.setText(String.valueOf(profile.getAccounts().get(0).getBalance()));
    }
    if (selectAccountType != null) {
      selectAccountType.getItems().addAll("Checking account", "Savings account",
          "BSU");
      selectAccountType.setValue("Savings account");
    }

    if (transferFromChoiceBox != null) {
      getInputsChoiceBox(transferFromChoiceBox);
    }

    if (transferToChoiceBox != null) {
      getInputsChoiceBox(transferToChoiceBox);
    }

    if (payFromChoiceBox != null) {
      getInputsChoiceBox(payFromChoiceBox);
    }

    if (noCardsLabel != null) {
      updateCards();
    }
  }

  public Profile getProfile() {
    return profile;
  }

  private void getInputsChoiceBox(ChoiceBox<String> choiceBox) {
    List<AbstractAccount> accs = profile.getAccounts();
    for (AbstractAccount absAcc : accs) {
      choiceBox.getItems().add(absAcc.getAccNr());
    }
  }

  /**
   * initializes the switch process between tabs
   * 
   * @param event
   * @throws IOException
   */

  @FXML
  public void initializeTab(MouseEvent event) throws IOException {
    String tab = ((Label) ((AnchorPane) event.getSource()).getChildren().get(0)).getText();
    if (tab.equals("Home")) {
      tab = "Overview";
    }
    switchTab(tab);

  }

  /**
   * Switches the tab to according to the value given by initializeTab
   * 
   * @param tab
   * @throws IOException
   */

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

  /**
   * Updates the account-table with the values of the accounts belonging to the
   * profile
   * 
   */
  @FXML
  public void updateAccounts() {
    AnchorPane accountLabelAnchorPane = new AnchorPane();
    AnchorPane balanceLabelAnchorPane = new AnchorPane();
    accountLabelAnchorPane.setStyle("-fx-background-color: #214C69;");
    balanceLabelAnchorPane.setStyle("-fx-background-color: #214C69;");
    accountsTable.add(accountLabelAnchorPane, 0, 0);
    accountsTable.add(balanceLabelAnchorPane, 1, 0);

    int count = 1;
    if (profile.getAccounts().isEmpty()) {
      return;
    }
    for (AbstractAccount account : profile.getAccounts()) {
      AnchorPane accountAnchorPane = new AnchorPane();
      Label accountName = new Label(account.getName() + "\n" + account.getAccNr());
      accountName.setStyle("-fx-font-size: 10px; -fx-min-width: 100px; -fx-min-height: 20px;");
      accountName.setLayoutX(10);
      accountAnchorPane.getChildren().add(accountName);
      accountAnchorPane.setPrefSize(100, 50);
      accountsTable.add(accountAnchorPane, 0, count);

      AnchorPane balanceAnchorPane = new AnchorPane();
      Label accountBalance = new Label(String.valueOf(account.getBalance()));
      accountBalance.setStyle("-fx-font-size: 10px; -fx-min-width: 100px; -fx-min-height: 20px;");
      accountBalance.setLayoutX(10);
      balanceAnchorPane.getChildren().add(accountBalance);
      balanceAnchorPane.setPrefSize(100, 50);
      accountsTable.add(balanceAnchorPane, 1, count);
      count += 1;
    }

  }

  /**
   * updates the transaction-table in spending page when the profile transfers or
   * pays
   * 
   * @throws IOException
   */
  @FXML
  public void updateTransaction() throws IOException {
    int size = profilesAccess.getTransactions(profile.getEmail()).size();
    if (size == 0) {
      return;
    }

    // logikk
    Transaction[] original = profilesAccess.getTransactions(profile.getEmail())
        .toArray(new Transaction[size]);
    Transaction[] reversed = new Transaction[10];
    int count0 = 0;
    while (count0 < reversed.length && count0 < original.length) {
      reversed[count0] = original[original.length - (count0 + 1)];
      count0++;
    }
    //

    int count = 1;
    for (Transaction transaction : reversed) {
      if (transaction == null) {
        break;
      }

      // logikk
      AbstractAccount acc1 = profile.getAccounts().stream()
          .filter(account -> account.getAccNr().equals(transaction.getTransactionTo()))
          .findFirst()
          .orElse(null);
      // profile.findAbstractAccountOfAProfile(transaction.getTransferTo());
      AbstractAccount acc2 = profile.getAccounts().stream()
          .filter(account -> account.getAccNr().equals(transaction.getTransactionFrom()))
          .findFirst()
          .orElse(null);
      // profile.findAbstractAccountOfAProfile(transaction.getTransferFrom());
      //

      AnchorPane accountAnchorPane = new AnchorPane();
      AnchorPane amountAnchorPane = new AnchorPane();
      Label accountLabel = new Label(transaction.getTransactionFrom());
      Label amountLabel;
      String message;
      if (acc1 != null && acc2 != null) {
        message = " (from Transfer)";
      } else {
        message = " (from Payment)";
      }
      amountLabel = new Label(String.valueOf((transaction.getAmount())) + message);
      accountLabel.setLayoutX(10);
      accountLabel.setLayoutY(8);
      amountLabel.setLayoutX(20);
      amountLabel.setLayoutY(8);
      accountAnchorPane.getChildren().add(accountLabel);
      amountAnchorPane.getChildren().add(amountLabel);
      System.out.println("kommer frem til trasactiontable");
      transactionTable.add(accountAnchorPane, 0, count);
      transactionTable.add(amountAnchorPane, 1, count);
      System.out.println("kom gjennom");
      BorderStroke borderStroke = new BorderStroke(
          Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
      Border tableBorder = new Border(borderStroke);
      transactionTable.setBorder(tableBorder);

      count++;
    }
  }

  /**
   * Handles the switch from login page to signup page
   * 
   * @param event
   */

  @FXML
  public void handleSignUpClick(MouseEvent event) {
    labelGoTo(event, "Register", signUpButton);
  }

  /**
   * Handles mouseclick to log out
   * 
   * @param event
   */

  @FXML
  public void switchTabToLoginPage(MouseEvent event) {
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

  /**
   * Handles mouseclick to log out
   * 
   * @param event
   * @param source the name of the fxml (without ".fxml")
   * @param label  fx-id of the label that leads to source
   */
  private void labelGoTo(MouseEvent event, String source, Label label) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(source + ".fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      Stage stage = (Stage) label.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * /**
   * handles mouse click of an AnchorPane to a specific fxml
   * 
   * @param event
   * @param source     the name of the fxml (without ".fxml")
   * @param anchorPane the fx-id of the AnchorPane that leads to source
   */
  private void AnchorPaneGoTo(MouseEvent event, String source, AnchorPane anchorPane) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(source + ".fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);
      Stage stage = (Stage) anchorPane.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * handles mouse click of a button to a specific fxml
   * 
   * @param event
   * @param source the name of the fxml (without ".fxml")
   * @param button the fx-id of the button that leads to source
   */
  private void buttonGoTo(MouseEvent event, String source, Button button) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource(source + ".fxml"));
      System.out.println(1);
      Parent root = loader.load();
      System.out.println(1);
      Scene scene = new Scene(root);
      Stage stage = (Stage) button.getScene().getWindow();
      stage.setScene(scene);
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println(e);
      loginError.setText(e.getMessage());
    }
  }

  /**
   * handles mouse click of a label that goes to a fxml that handles transactions
   * 
   * @param event
   */
  @FXML
  public void goToTransfer2(MouseEvent event) {
    labelGoTo(event, "Transfer", transferSavingsButton);
  }

  /**
   * handles mouse click of a label that goes to a fxml that handles creation of
   * new account
   * 
   * @param event
   */
  @FXML
  public void goToAccount(MouseEvent event) {
    labelGoTo(event, "NewAccount", newSavingAccountButton);
  }

  /**
   * handles mouse click that goes to a fxml that handles creation of new account
   * 
   * @param event
   */
  @FXML
  public void goToAccount2(MouseEvent event) {
    labelGoTo(event, "NewAccount", newAccountButton);
  }

  /**
   * handles Pay button in Payments
   * 
   * @param event
   */
  @FXML
  public void goToPay(MouseEvent event) {
    AnchorPaneGoTo(event, "Pay", goToPayButton);
  }

  /**
   * handles Transfer button in Payments
   * 
   * @param event
   */
  @FXML
  public void goToTransfer(MouseEvent event) {
    AnchorPaneGoTo(event, "Transfer", goToTransferButton);
  }

  /**
   * handles Settings button in Profile
   * 
   * @param event
   */
  @FXML
  public void goToSettings(MouseEvent event) {
    AnchorPaneGoTo(event, "Settings", settingsButton);
  }

  @FXML
  public void goToCards(MouseEvent event) {
    AnchorPaneGoTo(event, "Cards", cardsButton);
  }

  /**
   * Handles the payment process when pay button is clicked, and saves the
   * information
   * 
   * @param event
   */
  @FXML
  public void handlePayment(MouseEvent event) {
    String payFrom = payFromChoiceBox.getValue();
    String accPersonToPay = payTo.getText();
    int amount = Integer.parseInt(payAmount.getText());
    SpendingsAccount acc1 = null; // account paying (this.profile)
    SpendingsAccount acc2 = null; // person payed to

    if (payFrom.isEmpty() || accPersonToPay.isEmpty() || payAmount.getText().isEmpty()) {
      feedbackInPay.setText("Please fill in the fields");
    } else {
      try {
        acc1 = (SpendingsAccount) profile.getAccounts().stream().filter(account -> account.getAccNr().equals(payFrom))
            .filter(account -> account instanceof SpendingsAccount).findFirst()
            .orElse(null);
        // acc1 = profile.findSpendingsAccount(payFrom);
        acc2 = (SpendingsAccount) profilesAccess.getProfiles()
            .stream()
            .flatMap(profile -> profile.getAccounts().stream())
            .filter(account -> account.getAccNr().equals(accPersonToPay))
            .findFirst().orElse(null);

        acc1.pay(acc2, amount);
        feedbackInPay.setText("Payment successful!");
        payFromChoiceBox.setValue("");
        payTo.clear();
        payAmount.clear();
        writeInfo();
        profilesAccess.updateProfilesInfo(acc2.getProfile());

        profilesAccess.writeTransactions(acc1, acc2);

      } catch (Exception e) {
        feedbackInPay.setText("Something went wrong");
        feedbackInPay.setFill(Color.RED);
      }
    }
  }

  /**
   * handles the tranfering process when transfer button is clicked, and saves the
   * new information
   * 
   * @param event
   */

  @FXML
  public void handleTransfer(MouseEvent event) {
    String fromAccountChoiceBox = transferFromChoiceBox.getValue();
    String toAccountChoiceBox = transferToChoiceBox.getValue();
    if (transferAmount.getText().isEmpty())
      feedbackInTransfer.setText("Fill in amount");
    if (transferAmount.getText().isEmpty())
      feedbackInTransfer.setText("Fill in amount");
    int amount = Integer.parseInt(transferAmount.getText());

    AbstractAccount acc1 = null;
    AbstractAccount acc2 = null;

    try {
      acc1 = profile.getAccounts().stream().filter(account -> account.getAccNr().equals(fromAccountChoiceBox)) // fromAccount
          .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find account 1"));

      // acc1 = profile.findAbstractAccountOfAProfile(fromAccountChoiceBox);
      acc2 = profile.getAccounts().stream().filter(account -> account.getAccNr().equals(toAccountChoiceBox)) // toAccount
          .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find account 2"));
      acc2.transferFrom(acc1, amount);
      profilesAccess.writeTransactions(acc1, acc2);
      writeInfo();

      transferAmount.setText("");
      transferFromChoiceBox.setValue("");
      transferToChoiceBox.setValue("");
      feedbackInTransfer.setFill(Color.BLACK);
      feedbackInTransfer.setText("Transfer completed!");
    } catch (IllegalArgumentException e) {
      feedbackInTransfer.setText("Something went wrong");
      feedbackInTransfer.setFill(Color.RED);
    }
  }

  /**
   * creates new account when a given mouse event happens
   * 
   * @param event
   */
  @FXML
  public void createNewAccount(MouseEvent event) {
    String type = selectAccountType.getValue();
    String name = giveAccountName.getText();
    int numAccounts = profile.getAccounts().size();
    AbstractAccount account = null;

    if (name.isEmpty()) {
      feedbackInNewAccount.setText("Fill the field");
      feedbackInNewAccount.setFill(Color.RED);
    }

    else {
      String[] validTypes = { "BSU", "Checking account", "Savings account" };

      if (type.equals(validTypes[0])) {
        account = new BsuAccount(name, profile);
      }

      else if (type.equals(validTypes[1])) {
        account = new SpendingsAccount(name, profile);
      }

      else if (type.equals(validTypes[2])) {
        account = new SavingsAccount(name, profile);
      }

      try {
        profile.addAccount(account);
      } catch (IllegalArgumentException e) {
        feedbackInNewAccount.setText(e.getMessage());
        feedbackInNewAccount.setFill(Color.RED);
      }

      if (numAccounts + 1 == profile.getAccounts().size()) {
        feedbackInNewAccount.setText("New account created!");
      }

      giveAccountName.setText("");
      writeInfo();
    }
  }

  /**
   * opens a fxml where you can delete an account
   * 
   * @param event
   * @throws IOException
   */
  @FXML
  public void handleDeleteAccountStage1(MouseEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteAccount.fxml"));
    AnchorPane deleteAccount = loader.load();
    Stage stage = new Stage();
    stage.setScene(new Scene(deleteAccount));
    stage.setTitle("Delete Account");
    stage.initModality(Modality.APPLICATION_MODAL);
    stage.show();

  }

  /**
   * deletes the account with the written name in the text field
   *
   * @param event
   */
  @FXML
  public void handleDeleteAccountStage2(MouseEvent event) {
    String accountToBeDeleted = deleteAccountName.getText();
    AbstractAccount acc = null;
    acc = profile.getAccounts().stream().filter(account -> account.getName().equals(accountToBeDeleted))
        .findFirst()
        .orElse(null);
    try {
      profile.removeAccount(acc);
      writeInfo();
      Stage stage = (Stage) deleteAccount.getScene().getWindow();
      stage.close();
    } catch (Exception e) {
      feedbackInDeleteAccount.setText("Cannot find account");
      feedbackInDeleteAccount.setFill(Color.RED);
    }
  }

  /**
   * Saves new information
   * 
   */
  private void writeInfo() {
    if (profilesAccess.updateProfilesInfo(profile)) {
      System.out.println(987);
    }

  }

  /**
   * Handles mouseclick on the login-button
   * 
   * @param event
   * @throws Exception
   */

  @FXML
  public void handleLoginButton(MouseEvent event) {
    try {
      String email = emailInput.getText();
      String password = passwordInput.getText();
      List<Profile> profiles = profilesAccess.getProfiles();
      profile = profiles.stream()
          .filter(profile -> profile.getPassword().equals(password) && profile.getEmail().equals(email))
          .findFirst().orElseThrow(() -> new Exception("Invalid email or password"));
      buttonGoTo(event, "Overview", loginButton);
    } catch (Exception e) {
      loginError.setText(e.getMessage());
      e.printStackTrace();
    }
  }

  /**
   * Deletes this profile
   * 
   * @param event
   */
  @FXML
  public void handleDeleteProfile(MouseEvent event) {
    profilesAccess.deleteProfile(profile);
    labelGoTo(event, "Login", deleteProfileButton);
  }

  /**
   * handles the changes in profile and saves it
   * 
   * @param event
   */
  @FXML
  public void handleUpdateSettings(MouseEvent event) {
    String newNum = changeNumberTo.getText();
    String newPassword = changePasswordTo.getText();
    String newPassword2 = confirmChangePassword.getText();

    try {
      if (!newNum.isEmpty())
        profile.changeTlf(newNum);
      if (!(newPassword.isEmpty()) && !(newPassword2.isEmpty()) && newPassword.equals(newPassword2))
        profile.changePassword(newPassword2);
    } catch (IllegalArgumentException e) {
      feedbackInSettings.setText(e.getMessage());
    }

    feedbackInSettings.setText("Update successfull!");
    writeInfo();
  }

  /**
   * Handles logic for registering new profile
   * 
   */
  @FXML
  public void register() {
    if (password.getText().equals(passwordConfirm.getText())) {
      try {
        if (alreadyRegistered()) {
          throw new IllegalArgumentException("Account already registered");
        }
        profile = new Profile(fullName.getText(), email.getText(), phoneNr.getText(), password.getText());
        SpendingsAccount account = new SpendingsAccount("Spendings account", profile);
        account.add(100);
        profile.addAccount(account);
        writeInfo();

        Stage primaryStage = (Stage) registerButton.getScene().getWindow();

        primaryStage.setTitle("Bankapp - Overview");

        FXMLLoader tabLoader = new FXMLLoader(getClass().getResource("Overview.fxml"));
        Parent tabPane = tabLoader.load();
        Scene tabScene = new Scene(tabPane);

        primaryStage.setScene(tabScene);
        primaryStage.show();
      } catch (Exception e) {
        registerError.setText(e.getMessage());
      }
    } else {
      registerError.setText("Passwords do not match");
    }
  }

  /**
   * checks if new registered profile already exists or not
   * 
   * @return if profile already exists
   */

  private boolean alreadyRegistered() {
    try {
      List<Profile> profiles = profilesAccess.getProfiles();
      for (Profile profile : profiles) {
        if (profile.getEmail().equals(email.getText()) || profile.getTlf().equals(phoneNr.getText()))
          return true;
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException();
    }
  }
}
