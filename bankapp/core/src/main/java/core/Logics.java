package core;

import java.util.ArrayList;
import java.util.List;

import core.accounts.AbstractAccount;
import core.accounts.SpendingsAccount;

public class Logics {
  // private static RemoteProfilesAccess profilesAccess;
  private static final String endpointBaseUri = "http://localhost:8080/profiles/";

  public static Transaction[] getReveredTransactionsArray(List<Transaction> transactions) {
    Transaction[] original = transactions.toArray(new Transaction[transactions.size()]);
    Transaction[] reversed = new Transaction[10];
    int count0 = 0;
    while (count0 < reversed.length && count0 < original.length) {
      reversed[count0] = original[original.length - (count0 + 1)];
      count0++;
    }
    return reversed;
  }

  public static SpendingsAccount findOverallSpendingsAccount(String accountNr, List<Profile> profiles) {
    SpendingsAccount spendingsAccount;
    spendingsAccount = (SpendingsAccount) profiles.stream()
        .flatMap(profile -> profile.getAccounts().stream())
        .filter(account -> (account instanceof SpendingsAccount))
        .filter(account -> account.getAccNr().equals(accountNr))
        .findFirst().orElse(null);

    return spendingsAccount;
  }

  public static Profile checkProfile(Profile profile, String password) {
    if (profile.getPassword().equals(password)) {
      return profile;
    }
    throw new IllegalArgumentException("Password does not match profile");
  }

  public static void checkAlreadyRegistered(List<Profile> profiles, String email, String phoneNr) {
    for (Profile profile : profiles) {
      if (profile.getEmail().equals(email) || profile.getTlf().equals(phoneNr))
        throw new IllegalArgumentException("Account already registered");
    }
  }

  public static void main(String[] args) {
  }
}
