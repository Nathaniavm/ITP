package bankapp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BankCardTest {
    @Nested
    @DisplayName("Testing the get functions")
    public class gettersTest {
        private BankCard bankCard1;
        BankCard bankCard2;
        BankCard bankCard3;

        @BeforeEach
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
