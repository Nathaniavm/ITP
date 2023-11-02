package springboot;

import java.util.List;

import org.springframework.stereotype.Service;

import core.Profile;
import json.ProfileInformationManagement;

@Service
public class ProfilesService {

  private static final String currentDir = System.getProperty("user.dir").substring(0,
      System.getProperty("user.dir").length() - 11);
  private static final String file = currentDir + "/core/src/main/java/json/ProfileInformation.json";

  public List<Profile> getProfiles() {
    try {
      List<Profile> profiles = ProfileInformationManagement.readFromFile(file);
      return profiles;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Profile getProfile(String email) {
    return getProfiles().stream().filter(profile -> profile.getEmail().equals(email)).findFirst().orElse(null);
  }

  public void updateProfile(Profile profile) {
    try {
      ProfileInformationManagement.writeInformationToFile(profile, file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
