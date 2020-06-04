import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Hashing {
	public String generateStrongPasswordHash(String password) {

		byte[] salt;
		try {
			salt = getSalt();
			return generateStrongPasswordHash(password, salt);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}
	public String generateStrongPasswordHash(String password, String salt) {
		byte[] byteSalt = new BigInteger(salt,16).toByteArray();
		return generateStrongPasswordHash(password, rmv(byteSalt));
	}
	private byte[] rmv(byte[] bArr) {
		if(bArr[0] == 0) {
			byte[] rArr = new byte[bArr.length-1];
			for(int i = 1; i < bArr.length; i++) {
				rArr[i-1] = bArr[i];
			}
			return rArr;
		}else {
			return bArr;
		}
	}
	public String generateStrongPasswordHash(String password, byte[] salt) {
		int iterations = 1000;
		char[] chars = password.toCharArray();

		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf;
		try {
			skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return iterations + ":" + toHex(salt) + ":" + toHex(hash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (InvalidKeySpecException e) {
			// TODO Auto-generated catch block
			return null;
		}

	}

	private byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}

	private String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if (paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
	}

}
