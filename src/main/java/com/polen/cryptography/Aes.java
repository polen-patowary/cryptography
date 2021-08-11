package com.polen.cryptography;

import java.security.MessageDigest;
import java.util.Arrays;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.engines.RijndaelEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;

/**
 * @author polen
 *
 */
public class Aes {

	private static String SECRET_KEY_STR = "bAdlXEhj0XktXQOwtou8aFXTxieM2HbRsrbfghts5frxsok=";
	private static byte[] SALT_BYTES = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static byte[] PASSWORD_BYTES;

	public static byte[] crypt(byte[] inputData, Boolean encrypt) {
		byte[] newData = null;
		try {
			// Hash the secret key with SHA-256
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(SECRET_KEY_STR.getBytes("UTF-8"));
			PASSWORD_BYTES = md.digest();

			PKCS5S2ParametersGenerator key = new PKCS5S2ParametersGenerator();
			key.init(PASSWORD_BYTES, SALT_BYTES, 1000);
//		Equivalent to .NET class
//		Rfc2898DeriveBytes key = new Rfc2898DeriveBytes(PASSWORD_BYTES, SALT_BYTES, 1000);

			RijndaelEngine rijndael = new RijndaelEngine();
			ParametersWithIV iv = ((ParametersWithIV) key.generateDerivedParameters(256, 128));

//		RijndaelManaged AES = new RijndaelManaged();
//      AES.Key = key.GetBytes(32);
//      AES.IV = key.GetBytes(16); 
//		AES.Mode = CipherMode.CBC;

			BufferedBlockCipher cipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(rijndael));
			cipher.init(encrypt, iv);

//		MemoryStream ms = new MemoryStream();
//		CryptoStream cs = new CryptoStream(ms, (encrypt ? AES.CreateEncryptor() :AES.CreateDecryptor()), CryptoStreamMode.Write);

			newData = new byte[cipher.getOutputSize(inputData.length)];
			int l = cipher.processBytes(inputData, 0, inputData.length, newData, 0);
			l += cipher.doFinal(newData, l);
			if (l < newData.length) {
				Arrays.copyOfRange(newData, 0, l);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return newData;
	}
}
