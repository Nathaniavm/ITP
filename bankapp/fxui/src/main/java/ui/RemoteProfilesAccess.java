package ui;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.util.List;

import core.Profile;
import core.Transaction;
import core.Accounts.SpendingsAccount;

public class RemoteProfilesAccess {
  private final URI endpointBaseUri;

  private static final String APPLICATION_JSON = "application/json";

  private static final String CONTENT_TYPE_HEADER = "Content-Type";

  private ObjectMapper objectMapper;

  public RemoteProfilesAccess(URI endpointBaseUri) {
    this.endpointBaseUri = endpointBaseUri;
    objectMapper = new ObjectMapper();
  }

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

  public Profile getProfile(String email) {
    Profile profile = null;
    HttpRequest request = HttpRequest.newBuilder(profileUri(email)).GET().build();
    try {
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      profile = objectMapper.readValue(response.body(), Profile.class);
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return profile;
  }

  private URI profileUri(String email) {
    return endpointBaseUri.resolve(email);
  }

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

  public List<Transaction> getTransactions(String email) {
    List<Transaction> transactions = null;
    final HttpRequest request = HttpRequest.newBuilder(profileUri(email + "/transactions")).GET().build();
    try {
      System.out.println(endpointBaseUri.resolve(profileUri(email + "/transactions")));
      final HttpResponse<String> response = HttpClient.newBuilder().build().send(request,
          HttpResponse.BodyHandlers.ofString());
      transactions = objectMapper.readValue(response.body(), new TypeReference<List<Transaction>>() {
      });
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    }
    return transactions;
  }

  
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
  public static void main(String[] args) throws URISyntaxException {
    RemoteProfilesAccess remote = new RemoteProfilesAccess(new URI("localhost:8080/profiles/"));
  }
}
