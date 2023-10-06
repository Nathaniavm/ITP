package core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ProfileInformationManagement {

        public void writeInformationToFile(Profile profile, String filename)
                        throws StreamWriteException, DatabindException, IOException {

                File file = new File(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                List<Profile> profiles;

                try {
                        profiles = readFromFile(filename);
                }

                catch (Exception e) {
                        profiles = new ArrayList<>();
                }
                // sjekk om profil allerede er lagret
                if (!profiles.stream().anyMatch(ob -> ob.getEmail().equals(profile.getEmail()))) {
                        profiles.add(profile);
                } else {
                        Profile oldProfile = profiles.stream().filter(ob -> ob.getEmail().equals(profile.getEmail()))
                                        .findFirst().orElse(null);
                        profiles.set(profiles.indexOf(oldProfile), profile);
                }

                objectMapper.writeValue(file, profiles);

        }

        public List<Profile> readFromFile(String filename) throws StreamReadException, DatabindException, IOException {
                File file = new File(filename);
                ObjectMapper objectMapper = new ObjectMapper();
                // Profile profile = objectMapper.readValue(file, Profile.class);
                List<Profile> profiles = objectMapper.readValue(file, new TypeReference<List<Profile>>() {
                });
                // System.out.println(profiles.get(0));
                // System.out.println(profiles.get(0).getName());
                // System.out.println(profiles.get(0).getAccounts().get(0).getProfile());
                // System.out.println(profiles.get(0).getAccounts().get(0).getBalance());
                // System.out.println(profiles.get(0).getAccounts().get(0).getAccNr());
                return profiles;
        }

        public static void main(String[] args) throws IOException {

                ProfileInformationManagement management = new ProfileInformationManagement();
                Profile profile1 = new Profile("Klein Cornolis", "Klein@gmail.com", "12345678",
                                "passord13214212");
                Profile profile2 = new Profile("Philip Vu Lam", "Philip@gmail.com",
                                "87654321",
                                "passord123");
                Profile NTNU = new Profile("NTNU Gløshaugen", "NTNU@ntnu.no", "12345678",
                                "Administrator59");

                profile1.createAccount("Savings");
                profile2.createAccount("Hei");
                profile2.createAccount("Philips savings account");
                profile1.getAccounts().get(0).createBankCard();
                profile1.getAccounts().get(0).add(472189);

                Account sellerAccount = new Account("NTNU", NTNU);
                Bill bill = new Bill(100, "NTNU", "NTNU Gløshaugen", sellerAccount,
                                profile1.getAccounts().get(0), profile1);

                management.writeInformationToFile(profile1,
                                "bankapp/core/src/main/java/Files/ProfileInformation.json");
                management.writeInformationToFile(profile2,
                                "bankapp/core/src/main/java/Files/ProfileInformation.json");

                management.readFromFile("bankapp/core/src/main/java/Files/ProfileInformation.json");

        }

}
