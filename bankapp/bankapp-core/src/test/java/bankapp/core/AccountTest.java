package bankapp.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;

public class AccountTest {

    @Test
    public void testConstructor(){
        Account newAcc = new Account("Acc1");
        String name = newAcc.getName(); 
        assertEquals("Acc1", name);
    }

    @Test 
    public void testBalance(){
        Account newAcc = new Account("Acc2");
        newAcc.add(1500);
        newAcc.remove(500);
        assertEquals(1000, newAcc.getBalance());
    }

    @Test 
    public void testTransfer(){
        Account acc1 = new Account("acc1");
        Account acc2 = new Account("acc2");
        acc1.add(100); 
        
        assertThrows(IllegalArgumentException.class, () -> acc2.transferTo(acc1, 150));
    }




    
}
