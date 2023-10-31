package json;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Bill;
import core.Profile;
import core.Accounts.SpendingsAccount;

/**
 * Class that provides methods for managing profile information including
 * writing profiles to file, updating profiles and reading profiles from a file,
 * deleting a specific profile from file, and clearing the file
 */
public class ProfileInformationManagement {

  /**
   * Writes the new information of a profile to a file
   * 
   * @param profile  The profile to be written to the file
   * @param filename The name of the file where the profile information will be
   *                 stored
   * @throws StreamWriteException If an error occurs while writing to the file
   * @throws DatabindException    If there is an issue with data binding during
   *                              the serialization
   * @throws IOException          If there are genereal I/O errors during file
   *                              handling
   */
  public static void writeInformationToFile(Profile profile, String filename)
      throws StreamWriteException, DatabindException, IOException {

    System.out.println("Klarte meg hit");
    File file = new File(filename);
    if (!(file.exists())) {
      throw new IOException("File does not exists");
    }
    System.out.println("I'm here now");
    ObjectMapper objectMapper = new ObjectMapper();
    List<Profile> profiles;

    try {
      profiles = readFromFile(filename);
    }

    catch (Exception e) {
      profiles = new ArrayList<>();
    }

    if (!profiles.stream().anyMatch(ob -> ob.getEmail().equals(profile.getEmail()))) {
      profiles.add(profile);
    } else {
      Profile oldProfile = profiles.stream().filter(ob -> ob.getEmail().equals(profile.getEmail()))
          .findFirst().orElse(null);
      profiles.set(profiles.indexOf(oldProfile), profile);
    }

    objectMapper.writeValue(file, profiles);

  }

  /**
   * Reads and returns a list of profiles from a file
   * 
   * @param filename The name of the file containing profiles informations that
   *                 one wants to read from
   * @return A list of profiles that is read from the file
   * @throws StreamReadException If an error occurs while writing to the file
   * @throws DatabindException   If there is an issue with data binding during the
   *                             serialization
   * @throws IOException         If there are genereal I/O errors during file
   *                             handling
   * 
   * 
   */
  public static List<Profile> readFromFile(String filename)
      throws StreamReadException, DatabindException, IOException {
    File file = new File(filename);
    if (!(file.exists())) {
      throw new IOException("File does not exists");
    }
    ObjectMapper objectMapper = new ObjectMapper();
    // Profile profile = objectMapper.readValue(file, Profile.class);
    List<Profile> profiles = objectMapper.readValue(file, new TypeReference<List<Profile>>() {
    });
    System.err.println();
    return profiles;
  }

  /**
   * Deletes a certain profile from the JSON-file
   * 
   * @param filename The name of the file where the profile information will be
   *                 stored
   * @param profile  The profile to be deleted
   * @throws StreamReadException If an error occurs while writing to the file
   * @throws DatabindException   If there is an issue with data binding during the
   *                             serialization
   * @throws IOException         If there are genereal I/O errors during file
   *                             handling
   */

  public static void deleteProfile(String filename, Profile profile)
      throws StreamReadException, DatabindException, IOException {
    List<Profile> profiles = new ArrayList<>(readFromFile(filename));
    System.out.println(profiles);
    String tlf = profile.getTlf();
    for (Profile profileRemove : profiles) {
      if (profileRemove.getTlf().equals(tlf)) {
        profiles.remove(profileRemove);
        break;
      }
    }
    System.out.println(profiles);

    File file = new File(filename);
    if (!(file.exists())) {
      throw new IOException("File does not exists");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.writeValue(file, profiles);

  }

  /**
   * Clears the JSON-file
   * 
   * @param filename The name of the file to be cleared
   * @throws StreamReadException If an error occurs while writing to the file
   * @throws DatabindException   If there is an issue with data binding during the
   *                             serialization
   * @throws IOException         If there are genereal I/O errors during file
   *                             handling
   */

  public static void clearFile(String filename) throws StreamWriteException, DatabindException, IOException {
    File file = new File(filename);
    file.setWritable(true);
    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
    outputStream.write("".getBytes());
    outputStream.flush();
    outputStream.close();
  }

  public static void main(String[] args) throws IOException {
    Profile profile1 = new Profile("Klein Cornolis", "Klein@gmail.com", "12121212",
        "passord13214212");
    Profile profile2 = new Profile("Philip Vu Lam", "Philip@gmail.com",
        "87654321",
        "passord123");
    Profile NTNU = new Profile("NTNU Gløshaugen", "NTNU@ntnu.no", "98989898",
        "Administrator59");

    SpendingsAccount acc1 = new SpendingsAccount("Savings", profile1);
    profile1.addAccount(acc1);
    SpendingsAccount acc2 = new SpendingsAccount("Hei", profile1);
    profile1.addAccount(acc2);
    SpendingsAccount acc3 = new SpendingsAccount("Philips savings account", profile2);
    profile2.addAccount(acc3);
    ((SpendingsAccount) profile1.getAccounts().get(0)).createBankCard();
    profile1.getAccounts().get(0).add(472189);

    SpendingsAccount sellerAccount = new SpendingsAccount("NTNU", NTNU);
    NTNU.addAccount(sellerAccount);
    Bill bill = new Bill(100, "NTNU", "NTNU Gløshaugen", sellerAccount,
        (SpendingsAccount) profile1.getAccounts().get(0), profile1);
    profile1.addBill(bill);
    writeInformationToFile(profile1,
        "./bankapp/core/src/main/java/json/ProfileInformation.json");
    writeInformationToFile(profile2, "./bankapp/core/src/main/java/json/ProfileInformation.json");

    List<Profile> profiles = readFromFile("./bankapp/core/src/main/java/json/ProfileInformation.json");
    System.out.println(profiles.get(0));
    System.out.println(profiles.get(0).getBills());

    // deleteProfile("./bankapp/core/src/main/java/json/ProfileInformation.json",
    // profile1);

    // deleteContent("./bankapp/core/src/main/java/json/ProfileInformation.json");

    // System.out.println(profiles.get(0));
    // System.out.println(profiles.get(0).getName());
    // System.out.println(profiles.get(0).getAccounts().get(0).getProfile());
    // System.out.println(profiles.get(0).getAccounts().get(0).getBalance());
    // System.out.println(profiles.get(0).getAccounts().get(0).getAccNr());

  }

}
