package core;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import core.accounts.SpendingsAccount;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class that creates a bankcard and connects the bankcard with a certain
 * account.
 * 
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class BankCard implements Serializable {
  private String cardholder;
  private static List<String> cardNrs = new ArrayList<>();
  private String cardNr;
  private SpendingsAccount account;
  private boolean cardBlocked;
  private static final Random RANDOM = new Random();

  /**
   * Sets the owner of the card and generates a cardnumber.
   *
   * @param cardholder Name of the owner of the card
   * 
   */
  public BankCard(@JsonProperty("cardholder") String cardholder,
      @JsonProperty("account") SpendingsAccount account) {
    this.cardholder = cardholder;
    setCardNr();
    while (cardNrs.contains(cardNr)) {
      setCardNr();
    }
    this.account = account;
    cardNrs.add(cardNr);
    cardBlocked = false;
  }

  /**
   * Generates a cardnumber.
   * 
   */
  private void setCardNr() {
    cardNr = "1248 1632 ";
    for (int i = 0; i < 8; i++) {
      if (cardNr.length() == 14) {
        cardNr += " ";
      }
      cardNr += RANDOM.nextInt(10);
    }
  }

  /**
   * Gets the cardholder.
   *
   * @return name of the cardholder
   */
  public String getCardholder() {
    return cardholder;
  }

  /**
   * Gets the account corresponding to this bankcard.
   *
   * @return this bankcards corresponding account
   * 
   */
  public SpendingsAccount getAccount() {
    return account;
  }

  /**
   * Gets the cardnumber.
   *
   * @return the cardnumber
   */
  public String getCardNr() {
    return cardNr;
  }

  /**
   * Method for blocking a card.
   * 
   */
  public void blockCard() {
    cardBlocked = true;
    
  }

  /**
   * Method for unblocking a card.
   * 
   */
  public void unblockCard() {
    cardBlocked = false;
  }

  public boolean isCardBlocked(){
    return cardBlocked;
  }
}
