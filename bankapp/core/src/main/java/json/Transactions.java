package json;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Profile;
import core.Accounts.AbstractAccount;
import core.Accounts.SpendingsAccount;

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class, property = "@id")
public class Transactions implements Serializable {

    // private final static String filename =
    // "bankapp/core/src/main/java/json/TransactionsOverview.json";

    private Profile profile;
    private AbstractAccount transactionTo;
    private int amount;

    /**
     * Saves information as a Transactions object
     * 
     * @param profile The paying profile
     * @param account The account being paid
     * @param amount  The amount being paid
     */
    public Transactions(@JsonProperty("profile") Profile profile,
            @JsonProperty("transactionTo") AbstractAccount account,
            @JsonProperty("amount") int amount) {
        this.profile = profile;
        this.transactionTo = account;
        this.amount = amount;
    }

    /**
     * Writes transactions to a file
     * 
     * @param transaction The transaction that is going to be written to file
     * @throws IOException If there are genereal I/O errors during file
     *                     handling
     */
    public static void writeTransactions(Transactions transaction, String filename) throws IOException {
        File file = new File(filename);
        if (!(file.exists())) {
            throw new IOException("File does not exists");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Transactions> transactions;

        try {
            transactions = readTransactions(filename);

        }

        catch (Exception e) {
            transactions = new ArrayList<>();
        }

        if (!transactions.stream().anyMatch(ob -> ob.getAmount() == (transaction.getAmount()))) {
            transactions.add(transaction);
        } else {
            Transactions oldTransactions = transactions.stream()
                    .filter(ob -> ob.getAmount() == (transaction.getAmount()))
                    .findFirst().orElse(null);
            transactions.set(transactions.indexOf(oldTransactions), transaction);
        }

        objectMapper.writeValue(file, transactions);
    }

    /**
     * Reads transactions from a file
     * 
     * @return Returns a list of the transactions in the given file
     * @throws IOException If there are genereal I/O errors during file
     *                     handling
     */
    public static List<Transactions> readTransactions(String filename) throws IOException {
        File file = new File(filename);
        if (!(file.exists())) {
            throw new IOException("File does not exists");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<Transactions> transactions = objectMapper.readValue(file, new TypeReference<List<Transactions>>() {
        });
        return transactions;
    }

    /**
     * Get the account that is being paid
     * 
     * @return The account that is getting paid
     */
    public AbstractAccount getTransactionTo() {
        return transactionTo;
    }

    /**
     * Get the amount that is being paid
     * 
     * @return The amount being paid
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Get the profile that is going to pay
     * 
     * @return The paying profile
     */
    public Profile getProfile() {
        return profile;
    }

}
