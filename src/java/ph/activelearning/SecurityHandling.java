package ph.activelearning;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
/**
 * @author jonathanseanestaya
 */
public class SecurityHandling {
    public static String encrypt(String strToEncrypt, String cipherType, String key) {
        String[] cipherTypeDescription = cipherType.split("/");
        String type = cipherTypeDescription[0];
        String encryptedString = null;
        Base64.Encoder encode = Base64.getEncoder();
        
        try {
            Cipher cipher = Cipher.getInstance(cipherType);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), type);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            encryptedString = encode.encodeToString(cipher.doFinal(strToEncrypt.getBytes()));

            //encryptedString = encode.encodeToString(cipher.doFinal());
        }
        catch(Exception e) {
            System.err.println(e);
        }
        
        return encryptedString;
    }
    
    public static String decrypt(String strToDecrypt, String cipherType, String key) {
        String[] cipherTypeDescription = cipherType.split("/");
        String type = cipherTypeDescription[0];
        String decryptedString = null;
        Base64.Decoder decode = Base64.getDecoder();
        
        try {
            Cipher cipher = Cipher.getInstance(cipherType);
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), type);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptedString = new String(cipher.doFinal(decode.decode(strToDecrypt)));
        }
        catch(Exception e) {
            System.err.println(e);
        }
        
        return decryptedString;
    }
}