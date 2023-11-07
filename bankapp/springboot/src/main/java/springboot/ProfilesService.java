package springboot;

import core.Profile;
import core.Transaction;
import java.io.IOException;
import java.util.List;
import json.ProfileInformationManagement;
import json.TransactionsPersistence;

import org.springframework.stereotype.Service;

/**
 * Service class for Springboot controller.
 */
@Service
public class ProfilesService {

  private static final String currentDir = System.getProperty("user.dir").substring(0,
      System.getProperty("user.dir").length() - 11);
  private static final String profInfo = currentDir + "/springboot/src/main/resources/ProfileInformation.json";
  private static final String transInfo = currentDir + "/springboot/src/main/resources/TransactionsOverview.json";

  /**
   * Fetches profile from json file.
   *
   * @return List of all profiles registered.
   */
  public List<Profile> getProfiles() {
    try {
      System.out.println(profInfo);
      List<Profile> profiles = ProfileInformationManagement.readFromFile(profInfo);
      return profiles;
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Could not get profiles");
    }
  }

  public Profile getProfile(String email) {
    return getProfiles().stream().filter(profile -> profile.getEmail().equals(email))
        .findFirst().orElse(null);
  }

  /**
   * Writes new profile/updates profile in json file.
   *
   * @param profile - profile to update.
   */
  public void updateProfile(Profile profile) {
    try {
      ProfileInformationManagement.writeInformationToFile(profile, profInfo);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * Delete specific profile
   * 
   * @param profile to be deleted
   */
  public void deleteProfile(Profile profile) {
    try {
      ProfileInformationManagement.deleteProfile(profInfo, profile);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }

  public List<Transaction> getTransactions(Profile profile) throws IOException {
    return TransactionsPersistence.getProfilesTransaction(profile, transInfo);
  }

  /**
   * Writes transactions to json.
   *
   * @param transaction to add to list of all transactions.
   */
  public void writeTransaction(Transaction transaction) {
    try {
      TransactionsPersistence.writeTransactions(transaction, transInfo);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
