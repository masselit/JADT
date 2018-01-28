package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * 
 * @author Dorian Thivolle
 *
 */
public class Encrypt {
	
	public static String MD5encrypt(String pwdBrut)
	{
		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        byte[] passBytes = pwdBrut.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<digested.length;i++){
            sb.append(Integer.toHexString(0xff & digested[i]));
        }
        String pwdHash = sb.toString();
        System.out.println("Mot de passe crypté : " + pwdHash);
        return pwdHash;
	}
}
