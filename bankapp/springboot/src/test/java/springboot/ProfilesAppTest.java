package springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Profile;
import core.Transaction;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@ContextConfiguration(classes = { ProfilesAppController.class, ProfilesService.class, ProfilesApp.class })
@WebMvcTest
public class ProfilesAppTest {
  @Autowired
  private MockMvc mockMvc;

  private ObjectMapper objectMapper;
  private Profile profile;

  @BeforeEach
  public void setup() throws Exception {
    objectMapper = new ObjectMapper();
    profile = new Profile("Dhillon Touch", "dhillon@hotmail.com", "23452345", "Lucky0504");
  }

  private String profilesUrl(String... segments) {
    String url = "/" + ProfilesAppController.PROFILES_SERVICE_PATH;
    for (String segment : segments) {
      url = url + "/" + segment;
    }
    return url;
  }

  @Test
  @DisplayName("Tests creation and deletion of profile through spring server")
  public void testUpdateDeleteProfile() throws Exception {
    String requestJson = objectMapper.writeValueAsString(profile);
    mockMvc.perform(MockMvcRequestBuilders.put(profilesUrl())
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestJson))
        .andExpect(MockMvcResultMatchers.status().isOk());

    mockMvc.perform(MockMvcRequestBuilders.delete(profilesUrl(profile.getEmail()))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  @DisplayName("Tests fetching of profiles through spring server")
  public void testGet_profiles() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(profilesUrl())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    try {
      List<Profile> profiles = objectMapper.readValue(result.getResponse().getContentAsString(),
          new TypeReference<List<Profile>>() {
          });
      assertTrue(profiles.stream().anyMatch(profile -> profile.getEmail().equals("klein@gmail.com")));
      assertTrue(profiles.stream().anyMatch(profile -> profile.getEmail().equals("adalovelace@gmail.no")));
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Tests fetching of specific profile through spring server")
  public void testGet_profile() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(profilesUrl("klein@gmail.com"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    try {
      Profile profile = objectMapper.readValue(result.getResponse().getContentAsString(),
          Profile.class);
      assertEquals("klein@gmail.com", profile.getEmail());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
  @DisplayName("Tests fetching of specific profiles transaction through spring server")
  public void getProfile_transactions() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(profilesUrl("adalovelace@gmail.no", "transactions"))
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    try {
      List<Transaction> transactions = objectMapper.readValue(result.getResponse().getContentAsString(),
          new TypeReference<List<Transaction>>() {
          });
      assertFalse(transactions.isEmpty());
      for (Transaction transaction : transactions) {
        assertTrue(transaction.getEmail().equals("adalovelace@gmail.no"));
      }
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }
}
