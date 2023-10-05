package bankapp.core;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class BalanceTest {
    private Balance balance = new Balance(0);

    @Test
    @DisplayName("Checks if balance increases according to desired usage")
    public void checkIncrease() {
        assertThrows(IllegalArgumentException.class, () -> balance.increase(0));
        assertThrows(IllegalArgumentException.class, () -> balance.increase(-100));

        balance.increase(400);
        assertTrue(balance.getBalance() == 400);
    }

    @Test
    @DisplayName("Checks if balance decreases according to desired usage")
    public void checkDecrease() {
        balance.increase(200);
        assertThrows(IllegalArgumentException.class, () -> balance.decrease(300));
        assertThrows(IllegalArgumentException.class, () -> balance.decrease(0));
        assertThrows(IllegalArgumentException.class, () -> balance.decrease(-300));

        balance.decrease(200);
        balance.increase(400);
        balance.decrease(100);
        assertTrue(balance.getBalance() == 300);
    }
}
