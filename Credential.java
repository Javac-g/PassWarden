package Vault;

import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Credential {
    private  String username;
    private  String email;
    private  String password;
    private final UUID node;

    public Credential(){

        Supplier<UUID> supplier = UUID::randomUUID;
        Stream<UUID> infiniteStream = Stream.generate(supplier);
        node = infiniteStream.findFirst().orElseThrow( ()-> new IllegalStateException("No UUID generated") );
        this.username = "username";
        this.email = "email";
        this. password = "password";
    }
    public String getUsername(){
        return username;
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
    public void setUsername(String username){
        this.username = username;
    };
    public void setPassword(String password){
        this.password = password;
    };
    public void setEmail(String email){
        this.email = email;
    };

    public UUID getNode(){
        return node;
    }
    @Override
    public String toString(){
        return "\nUsername: " + username + "\nPassword: " + password + "\nEmail: " + email;
    }
}
