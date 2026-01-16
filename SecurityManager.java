package Model;

import Vault.Credential;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class SecurityManager {

    private String token = "progressia";
    public static SecretKeySpec getKeyFromUUID(UUID uuid) {
        // UUID -> 16 bytes -> AES-128 key
        byte[] keyBytes = new byte[16];
        System.arraycopy(longToBytes(uuid.getMostSignificantBits()), 0, keyBytes, 0, 8);
        System.arraycopy(longToBytes(uuid.getLeastSignificantBits()), 0, keyBytes, 8, 8);
        return new SecretKeySpec(keyBytes, "AES");
    }
    private static byte[] longToBytes(long x) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(x & 0xFF);
            x >>= 8;
        }
        return result;
    }
    public static String encrypt(String data, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        // Generate random IV
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));

        // Return Base64 encoded IV + ciphertext
        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(combined);
    }
    public static String decrypt(String encryptedData, SecretKeySpec key) throws Exception {
        byte[] combined = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[16];
        byte[] ciphertext = new byte[combined.length - 16];
        System.arraycopy(combined, 0, iv, 0, 16);
        System.arraycopy(combined, 16, ciphertext, 0, ciphertext.length);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(ciphertext);
        return new String(decrypted, StandardCharsets.UTF_8);
    }
    public void process_encrypt(Credential credential) throws Exception {
        SecretKeySpec key = getKeyFromUUID(credential.getNode());
        credential.setUsername(encrypt(credential.getUsername(), key));
        credential.setEmail(encrypt(credential.getEmail(), key));
        credential.setPassword(encrypt(credential.getPassword(), key));


    }
    public void process_decrypt(Credential credential) throws Exception {
        SecretKeySpec key = getKeyFromUUID(credential.getNode());
        credential.setUsername(decrypt(credential.getUsername(), key));
        credential.setEmail(decrypt(credential.getEmail(), key));
        credential.setPassword(decrypt(credential.getPassword(), key));


    }
    public String getToken(){
        return token;
    }

}
