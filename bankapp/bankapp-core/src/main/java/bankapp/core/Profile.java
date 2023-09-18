package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private int tlf;
    private String password;
    private List<Account> accounts = new ArrayList<>();

    public void createAccount(String name){
        accounts.add(new Account(name));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTlf(int tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getTlf() {
        return tlf;
    }

    public String getPassword() {
        return password;
    }
}
