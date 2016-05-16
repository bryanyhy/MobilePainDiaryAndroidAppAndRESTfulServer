package bryan.mobilepaindiary.Utilities;

import java.security.MessageDigest;

/**
 * Created by Bryanyhy on 25/4/16.
 */

// Hash the user password for security reason
// Reference: http://www.mkyong.com/java/java-sha-hashing-example/
public class HashPassword {

    // Change the user's password input to hashed format
    public static String passwordToHash(String pw) {
        MessageDigest md = null;
        try
        {
            md = MessageDigest.getInstance("SHA-256");
            md.update(pw.getBytes("UTF-8"));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        byte byteData[] = md.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++)
        {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    // hashed the password input by user during login, and compare it to the hashed password saved in rest server
    public static boolean compareHash(String pw, String hash) {
        if (passwordToHash(pw).equals(hash))
        {
            return true;
        } else
        {
            return false;
        }
    }
}
