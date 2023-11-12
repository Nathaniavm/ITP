package springboot;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Profile;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
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
  public void testGet_profiles() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(profilesUrl())
        .accept(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
    try {
      List<Profile> profiles = objectMapper.readValue(result.getResponse().getContentAsString(),
          new TypeReference<List<Profile>>() {
          });
      Iterator<Profile> it = profiles.iterator();
      assertTrue(it.hasNext());
      Profile profile1 = it.next();
      assertTrue(it.hasNext());
      Profile profile2 = it.next();
      assertEquals("klein@gmail.com", profile1.getEmail());
      assertEquals("adalovelace@gmail.no", profile2.getEmail());
    } catch (JsonProcessingException e) {
      fail(e.getMessage());
    }
  }

  @Test
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
  // @Test
  // public void getProfile_transactions() throws Exception {
  //   MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(profilesUrl("klein@gmail.com","transactions"))
  //       .accept(MediaType.APPLICATION_JSON))
  //       .andExpect(MockMvcResultMatchers.status().isOk())
  //       .andReturn();
  //   try {
  //     Profile profile = objectMapper.readValue(result.getResponse().getContentAsString(),
  //         Profile.class);
  //     assertEquals("klein@gmail.com", profile.getEmail());
  //   } catch (JsonProcessingException e) {
  //     fail(e.getMessage());
  //   }
  // }

}
