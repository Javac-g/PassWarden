package Model;

import Vault.Credential;

import java.io.*;
import java.util.List;

public class DatabaseStorage {

    private static final String FILE_NAME = "db.dat";

    public static void save(List<Credential> database) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME,true))) {

            oos.writeObject(database);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save database", e);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Credential> load() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new java.util.ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (List<Credential>) ois.readObject();

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to load database", e);
        }
    }
}
