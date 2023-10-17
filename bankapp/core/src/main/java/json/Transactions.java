package json;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import core.Account;
import core.Profile;

public class Transactions {

    private final static String filename = "bankapp/core/src/main/java/json/TransactionsOverview.json";

    private Profile profile;
    private Account transactionTo;
    private int amount;

    /**
     * Saves information as a Transactions object
     * 
     * @param profile       The paying profile
     * @param transactionTo The account being paid
     * @param amount        The amount being paid
     */
    public Transactions(Profile profile, Account transactionTo, int amount) {
        this.profile = profile;
        this.transactionTo = transactionTo;
        this.amount = amount;
    }

    /**
     * Writes transactions to a file
     * 
     * @param transaction The transaction that is going to be written to file
     * @throws IOException If there are genereal I/O errors during file
     *                     handling
     */
    public static void writeTransactions(Transactions transaction) throws IOException {
        File file = new File(filename);
        if (!(file.exists())) {
            throw new IOException("File does not exists");
        }

        ObjectMapper objectMapper = new ObjectMapper();
        List<Transactions> transactions;

        try {
            transactions = readTransactions();

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
    public static List<Transactions> readTransactions() throws IOException {
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
    public Account getTransactionTo() {
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
    public Profile getprofile() {
        return profile;
    }

}
