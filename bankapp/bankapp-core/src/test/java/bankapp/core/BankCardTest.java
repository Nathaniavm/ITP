package bankapp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class BankCardTest {
    private BankCard bankCard1;
    private BankCard bankCard2;
    private BankCard bankCard3;

    @Before
    @DisplayName("setting up the different bankcards")
    public void setUp() {
        bankCard1 = new BankCard("Jane Smith");
        bankCard2 = new BankCard("John Doe");
        bankCard3 = new BankCard("Jayan Tayana");
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
            BankCard bankCard = new BankCard("Cardholder" + i);
            String cardNr = bankCard.getCardNr();
            assertFalse(cardNumberSet.contains(cardNr));
            cardNumberSet.add(cardNr);
        }

        assertTrue(numberOfCards == cardNumberSet.size());
    }
}
