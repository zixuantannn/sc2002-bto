import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {
    private static final String SECRET_KEY = "1234567890abcdef"; // 16-char key = 128-bit

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

    /*public static void main(String[] args) {
        String encryptedPassword = encrypt("password");
        System.out.println("Encrypted password: " + encryptedPassword);
    }*/
}
