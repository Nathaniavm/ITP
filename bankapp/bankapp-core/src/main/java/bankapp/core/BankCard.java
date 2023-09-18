package bankapp.core;

public class BankCard {
    private int cardNr;
    private String cardholder;
    
    
    public BankCard(int cardNr, String cardholder) {
        //Fiks logikk for hvordan kortnummer skal se ut, og navn som er tillat og sånt
        this.cardNr = cardNr;
        this.cardholder = cardholder;
    }


    public int getCardNr() {
        return cardNr;
    }


    public String getCardholder() {
        return cardholder;
    }

    
}
