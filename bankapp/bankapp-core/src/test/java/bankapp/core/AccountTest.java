package bankapp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    /**
     * Tests if the constructor makes an account with name equal to the input
     */
    @Test
    public void testConstructor(){
        Account newAcc = new Account("Acc1");
        String name = newAcc.getName(); 
        assertEquals("Acc1", name);
    }

    /**
     * tests if the transaction instruction works 
     */
    @Test 
    public void testBalance(){
        Account newAcc = new Account("Acc2");
        newAcc.add(1500);
        newAcc.remove(500);
        assertEquals(1000, newAcc.getBalance());
    }

    /**
     * tests transaction between two accounts, and if there will be an IllegalArgumentException when we try to tranfer more money than the balance in the account
     */
    @Test 
    public void testTransfer(){
        Account acc1 = new Account("acc1");
        Account acc2 = new Account("acc2");
        acc1.add(100); 
        
        assertThrows(IllegalArgumentException.class, () -> acc2.transferTo(acc1, 150));
        assertTrue(acc1.getBalance() == 100);

        acc2.transferTo(acc1, 50);
        assertTrue(acc1.getBalance() == acc2.getBalance());
    }

    @Test
    public void testAccountNr(){
        Account acc1 = new Account("acc1");
        Account acc2 = new Account("acc2");
        Account acc3 = new Account("acc3");
        Account acc4 = new Account("acc4");

        assertFalse(acc1.getAccNr() == acc2.getAccNr());
        assertFalse(acc1.getAccNr() == acc3.getAccNr());
        assertFalse(acc1.getAccNr() == acc4.getAccNr());

        assertFalse(acc2.getAccNr() == acc3.getAccNr());
        assertFalse(acc2.getAccNr() == acc4.getAccNr());

        assertFalse(acc3.getAccNr() == acc4.getAccNr());

    }

    




    
}
