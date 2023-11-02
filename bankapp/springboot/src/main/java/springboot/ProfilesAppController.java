package springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import core.Profile;

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

}
