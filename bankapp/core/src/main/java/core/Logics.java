package core;

import java.util.ArrayList;
import java.util.List;

import core.accounts.AbstractAccount;
import core.accounts.SpendingsAccount;



public abstract class Logics {
  //private static RemoteProfilesAccess profilesAccess;
  private static final String endpointBaseUri = "http://localhost:8080/profiles/";

  public static Transaction[] getReveredTransactionsArray(List<Transaction> transactions){
    Transaction[] original = transactions.toArray(new Transaction[transactions.size()]);
    Transaction[] reversed = new Transaction[10];
    int count0 = 0;
    while (count0 < reversed.length && count0 < original.length) {
      reversed[count0] = original[original.length - (count0 + 1)];
      count0++;
    } 
    return reversed;
  }

  /* 
  public AbstractAccount findOverallAbstractAccount(String accountNr){

  }*/

  public static void main(String [] args){
  }
}
