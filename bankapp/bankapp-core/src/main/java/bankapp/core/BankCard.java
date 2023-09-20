package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class BankCard {
    private String cardholder;
    private static List<String> cardNrs = new ArrayList<>();
    private String cardNr;

    /**
     * Sets the owner of the card and generates a cardNr
     * 
     * @param cardholder - name of the owner of the card
     */
    public BankCard(String cardholder) {
        this.cardholder = cardholder;
        setCardNr();
        while (cardNrs.contains(cardNr)) {
            setCardNr();
        }
        cardNrs.add(cardNr);
    }

    /**
     * Generates a cardnumber
     */
    private void setCardNr() {
        cardNr = "1248 1632 ";
        for (int i = 0; i < 8; i++) {
            if (cardNr.length() == 14) {
                cardNr += " ";
            }
            Integer rand = (int) (Math.random() * 10);
            cardNr += rand;
        }
    }

    /**
     * 
     * @return name of the cardholder
     */
    public String getCardholder() {
        return cardholder;
    }

    /**
     * 
     * @return the card number
     */
    public String getCardNr() {
        return cardNr;
    }
}
