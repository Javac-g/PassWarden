package Model;

import Vault.Credential;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Model {
    private List<Credential> dataBase = DatabaseStorage.load();
    private final static SecurityManager security = new SecurityManager();



    public void addCred(String username, String email, String password) throws Exception {
        Credential credential = new Credential();
        credential.setPassword(password);
        credential.setEmail(email);
        credential.setUsername(username);
        security.process_encrypt(credential);
        dataBase.add(credential);
        DatabaseStorage.save(dataBase);
    }

    public void findCredent(){
        System.out.println(dataBase);
    }
}
