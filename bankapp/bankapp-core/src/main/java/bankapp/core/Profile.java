package bankapp.core;

import java.util.ArrayList;
import java.util.List;

public class Profile {
    private String name;
    private String email;
    private String tlf;
    private String password;
    private List<Account> accounts = new ArrayList<>();

    public Profile(String name, String email, String tlf, String password) {
        if(!(name.contains(" "))){
            
        }
        if(password.length() < 8){
            throw new IllegalArgumentException("Password must contain at least 8 characters");
        }
        if(){

        }
        this.name = name;
        this.email = email;
        this.tlf = tlf;
        this.password = password;
    }

    public void createAccount(String name) {
        accounts.add(new Account(name));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getTlf() {
        return tlf;
    }

    public String getPassword() {
        return password;
    }
}
