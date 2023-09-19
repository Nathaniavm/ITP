package bankapp.core;
<<<<<<< HEAD
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.*;
import org.junit.Test;

public class ProfileTest {
    //checking 

    @Test
    public void checkConstructor(){
        Profile profile = new Profile("Ola Nordmann", "olaNordmann@gmail.com", "41904786", "password");
        
        assertTrue(profile.getName().equals("Ola Nordmann"));
        assertTrue(profile.getEmail().equals("olaNordmann@gmail.com"));
        assertTrue(profile.getTlf().equals("41904786"));
        assertTrue(profile.getPassword().equals("password"));
       
    }

    @Test
    public void checkTlfNumber(){
        Profile profile = new Profile("Name Name", "namename@stud.ntnu.no", "41978909", "password");
        profile.changeTlf("40278408");
        String s = profile.getTlf();
        assertTrue(s.equals("40278408"));
        assertThrows(IllegalArgumentException.class, () -> profile.changeTlf("20a02h0f"));
    }

    



=======

public class ProfileTest {
    // checking
>>>>>>> 6e98feb19ab156bc871344e06c23816f5004ef63
}
