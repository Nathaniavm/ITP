package core.Accounts;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import core.Profile;

/**
 * Creates a new BSU-Account to the given profile, with a given name
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class BSUAccount extends AbstractAccount implements Serializable {

  /**
   * Creates a new BSU-Account
   * 
   * @param name    The name of the account
   * @param profile The profile to make an account for
   */
  public BSUAccount(@JsonProperty("name") String name, @JsonProperty("profile") Profile profile) {
    super(name, profile);
  }

}
