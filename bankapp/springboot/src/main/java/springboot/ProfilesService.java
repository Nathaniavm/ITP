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
      List<Profile> testing = ProfileInformationManagement.readFromFile(file);
      return testing;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public Profile getProfile(String email) {
    return getProfiles().stream().filter(profile -> profile.getEmail().equals(email)).findFirst().get();
  }

  public void addProfile(Profile profile) {
    try {
      ProfileInformationManagement.writeInformationToFile(profile, file);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
