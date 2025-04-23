package utility;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * The {@code AESUtils} class provides utility methods for encrypting and
 * decrypting strings using the AES (Advanced Encryption Standard) symmetric
 * encryption algorithm.
 * 
 */
public class AESUtils {
    private static final String SECRET_KEY = "1234567890abcdef"; // 16-char key = 128-bit

    /**
     * Encrypts the given string using AES encryption with a fixed secret key.
     *
     * @param data the plaintext string to encrypt.
     * @return the encrypted string in Base64 encoding.
     * @throws RuntimeException if encryption fails.
     */
    public static String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting password", e);
        }
    }

    /**
     * Decrypts the given Base64-encoded string that was encrypted using AES.
     *
     * @param encryptedData the encrypted string in Base64 encoding.
     * @return the original decrypted plaintext string.
     * @throws RuntimeException if decryption fails.
     */
    public static String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decrypted);
        } catch (Exception e) {
            throw new RuntimeException("Error decrypting password", e);
        }
    }

}
