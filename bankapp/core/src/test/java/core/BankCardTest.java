package core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import core.Accounts.SpendingsAccount;

public class BankCardTest {
    private BankCard bankCard1;
    private BankCard bankCard2;
    private BankCard bankCard3;
    private SpendingsAccount account1;
    private SpendingsAccount account2;
    private SpendingsAccount account3;
    private Profile profile1;
    private Profile profile2;
    private Profile profile3;

    @Before
    @DisplayName("setting up the different bankcards")
    public void setUp() {
        profile1 = new Profile("Jane Smith", "jane@gmail.com", "12345678", "passord12");
        profile2 = new Profile("John Doe", "jane@gmail.com", "12345678", "passord12");
        profile3 = new Profile("Jayan Tayana", "jane@gmail.com", "12345678", "passord12");
        account1 = new SpendingsAccount("test account", profile1);
        account2 = new SpendingsAccount("test account", profile2); 
        account3 = new SpendingsAccount("test account", profile3);
        account1.createBankCard();
        account2.createBankCard();
        account3.createBankCard();
        bankCard1 = account1.getBankCard();
        bankCard2 = account2.getBankCard();
        bankCard3 = account3.getBankCard();
    }

    @Test
    @DisplayName("Testing is one gets the right cardholder")
    public void testGetCardholder() {
        assertEquals("John Doe", bankCard2.getCardholder());
        assertEquals("Jane Smith", bankCard1.getCardholder());
        assertEquals("Jayan Tayana", bankCard3.getCardholder());

        assertNotEquals("Jayan Tayana", bankCard2.getCardholder());
    }

    @Test
    @DisplayName("Testing if the card number is in the right format")
    public void testGetCardNrFormat() {
        assertTrue(bankCard1.getCardNr().matches("\\d{4} \\d{4} \\d{4} \\d{4}"));
    }

    @Test
    @DisplayName("Testing if one gets uniqe numbers when a card is made")
    public void testCardNrUniqness() {
        int numberOfCards = 100;
        Set<String> cardNumberSet = new HashSet<>();

        for (int i = 0; i < numberOfCards; i++) {
            BankCard bankCard = new BankCard("Cardholder" + i,account1);
            String cardNr = bankCard.getCardNr();
            assertFalse(cardNumberSet.contains(cardNr));
            cardNumberSet.add(cardNr);
        }

        assertTrue(numberOfCards == cardNumberSet.size());
    }
}
