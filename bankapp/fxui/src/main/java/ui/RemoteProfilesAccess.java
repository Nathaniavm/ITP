package ui;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Profile;
import core.Transaction;
import core.accounts.AbstractAccount;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.util.List;

/**
 * Class that gives access to saved profile information through REST API.
 */
public class RemoteProfilesAccess {
  private final URI endpointBaseUri;

  private static final String APPLICATION_JSON = "application/json";

  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private ObjectMapper objectMapper;

  public RemoteProfilesAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
    objectMapper = new ObjectMapper();
  }

  /**
   * Fetches list of all saved profiles.
   *
   * @return list of all saved profiles
   */
  public List<Profile> getProfiles() {
    List<Profile> profiles = null;
    final HttpRequest request = HttpRequest.newBuilder(endpointBaseUri).GET().build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      profiles = objectMapper.readValue(response.body(), new TypeReference<List<Profile>>() {
      });
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return profiles;
  }

  /**
   * Fetch profile with given email.
   *
   * @param email - email of profile to fetch
   * @return profile with given email
   */
  public Profile getProfile(String email) {
    Profile profile = null;
    HttpRequest request = HttpRequest.newBuilder(profileUri(email)).GET().build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      profile = objectMapper.readValue(response.body(), Profile.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException("Invalid email");
    }
    return profile;
  }

  private URI profileUri(String email) {
    return endpointBaseUri.resolve(email);
  }

  /**
   * Saves a new profile or updates an old one if given profile already exists.
   *
   * @param profile - Profile to save
   * @return boolean based on if the operation succeeded or not
   */
  public boolean updateProfilesInfo(Profile profile) {
    try {
      String json = objectMapper.writeValueAsString(profile);
      HttpRequest request = HttpRequest.newBuilder(endpointBaseUri)
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .PUT(BodyPublishers.ofString(json)).build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      return response.body() != null;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Delete given profile from saved data.
   *
   * @param profile - profile to be deleted
   * @return boolean based on if the operation succeeded or not
   */
  public boolean deleteProfile(Profile profile) {
    try {
      HttpRequest request = HttpRequest.newBuilder(profileUri(profile.getEmail()))
          .DELETE().build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      return response.body() != null;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Gets list of all transactions belonging to profile with given email.
   *
   * @param email - email of profile
   * @return list of transactions
   */
  public List<Transaction> getTransactions(String email) {
    List<Transaction> transactions = null;
    final HttpRequest request = HttpRequest.newBuilder(profileUri(email + "/transactions"))
        .GET().build();
    try {
      System.out.println(endpointBaseUri.resolve(profileUri(email + "/transactions")));
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      transactions = objectMapper.readValue(response.body(),
          new TypeReference<List<Transaction>>() {
          });
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return transactions;
  }

  /**
   * Saves given transaction.
   *
   * @param transaction - The transaction to save
   * 
   */
  public boolean writeTransaction(Transaction transaction) {
    try {
      String json = objectMapper.writeValueAsString(transaction);
      HttpRequest request = HttpRequest.newBuilder(endpointBaseUri.resolve("transaction"))
          .header(CONTENT_TYPE_HEADER, APPLICATION_JSON)
          .POST(BodyPublishers.ofString(json)).build();
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      return response.body() != null;
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public void writeTransactions(AbstractAccount acc1, AbstractAccount acc2) {
    writeTransaction(acc1.getTransaction().get(acc1.getTransaction().size() - 1));
    writeTransaction(acc2.getTransaction().get(acc2.getTransaction().size() - 1));
  }
}
