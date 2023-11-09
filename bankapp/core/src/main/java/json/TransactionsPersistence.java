package json;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.Profile;
import core.Transaction;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that provides methods for managing transactions including
 * writing transactions to file, reading transactions from file, finding
 * transactions associated with a specific profile from file, and clearing the
 * file.
 */

public class TransactionsPersistence implements Serializable {

  /**
   * Writes transactions to a file.
   * 
   * @param transaction The transaction that is going to be written to file
   * @param filename    The file written to
   * 
   * @throws IOException If there are genereal I/O errors during file
   *                     handling
   * 
   */
  public static void writeTransactions(Transaction transaction, String filename) throws IOException {
    File file = new File(filename);
    if (!(file.exists())) {
      throw new IOException("File does not exists");
    }

    ObjectMapper objectMapper = new ObjectMapper();
    List<Transaction> transactions;

    try {
      transactions = readTransactions(filename);
    } catch (Exception e) {
      transactions = new ArrayList<>();
    }

    transactions.add(transaction);
    objectMapper.writeValue(file, transactions);
  }

  /**
   * Reads transactions from a file.
   * 
   * @param filename The file read from
   * 
   * @return Returns a list of the transactions in the given file
   * 
   * @throws IOException If there are genereal I/O errors during file
   *                     handling
   * 
   */
  public static List<Transaction> readTransactions(String filename) throws IOException {
    File file = new File(filename);
    if (!(file.exists())) {
      throw new IOException("File does not exists");
    }
    ObjectMapper objectMapper = new ObjectMapper();
    List<Transaction> transactions = objectMapper.readValue(file,
        new TypeReference<List<Transaction>>() {
        });
    return transactions;
  }

  /**
   * Finds transactions associated with provided profile.
   * 
   * @param profile  The profile you want to find the associated transactions to
   * @param filename The file read from
   * 
   * @return Returns a list of the transactions associated with the provided
   *         profile
   * 
   * @throws IOException If there are genereal I/O errors during file
   *                     handling
   * 
   */
  public static List<Transaction> getProfilesTransaction(Profile profile, String filename) throws IOException {
    List<Transaction> profileTransaction;
    List<Transaction> transactions = readTransactions(filename);
    profileTransaction = transactions.stream().filter(t -> t.getEmail().equals(profile.getEmail()))
        .collect(Collectors.toList());
    return profileTransaction;
  }

  /**
   * Clears the JSON-file.
   * 
   * @param filename The name of the file to be cleared
   * @throws StreamReadException If an error occurs while writing to the file
   * @throws DatabindException   If there is an issue with data binding during the
   *                             serialization
   * @throws IOException         If there are genereal I/O errors during file
   *                             handling
   * 
   */
  public static void clearFile(String filename) throws StreamWriteException, DatabindException, IOException {
    byte[] bytes = "".getBytes(StandardCharsets.UTF_8);
    File file = new File(filename);
    file.setWritable(true);
    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
    outputStream.write(bytes);
    outputStream.flush();
    outputStream.close();
  }

}
