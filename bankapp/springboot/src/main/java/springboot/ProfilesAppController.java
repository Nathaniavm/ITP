package springboot;

import core.Profile;
import core.Transaction;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for Springboot server.
 */
@RestController
@RequestMapping(ProfilesAppController.PROFILES_SERVICE_PATH)
public class ProfilesAppController {

  public static final String PROFILES_SERVICE_PATH = "profiles";

  @Autowired
  private ProfilesService profilesService;

  @GetMapping
  public List<Profile> getProfiles() {
    return profilesService.getProfiles();
  }

  @GetMapping("/{email}")
  public Profile getProfile(@PathVariable String email) {
    return profilesService.getProfile(email);
  }

  @PutMapping
  public void updateProfile(@RequestBody Profile profile) {
    profilesService.updateProfile(profile);
  }

  @GetMapping("/{email}/transactions")
  public List<Transaction> getMethodName(@PathVariable String email) throws IOException {
    return profilesService.getTransactions(profilesService.getProfile(email));
  }

  @PostMapping("transaction")
  public void postMethodName(@RequestBody Transaction transaction) {
    profilesService.writeTransaction(transaction);
  }

  @DeleteMapping("/{email}")
  public void deleteProfile(@PathVariable String email){
    profilesService.deleteProfile(profilesService.getProfile(email));
  }
}
