package com.polen.cryptography;

import java.util.Base64;
import java.util.Scanner;

/**
 * @author polen
 *
 */
public class App {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter THE INPUT TEXT: ");
		String plainTextInput = sc.nextLine();
		System.out.println("PLAIN TEXT INPUT: " + plainTextInput);
		try {
			// ENCRYPT
			byte[] encryptedBytes = Aes.crypt(plainTextInput.getBytes("UTF-8"), true);
			String encryptedText = new String(Base64.getEncoder().encode(encryptedBytes));
			System.out.println("ENCRYPTED TEXT OUTPUT: " + encryptedText);

			// DECRYPT
			byte[] decryptedBytes = Aes.crypt(Base64.getDecoder().decode(encryptedText), false);
			String decryptedText = new String(decryptedBytes);
			System.out.println("DECRYPTED TEXT OUTPUT: " + decryptedText);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		sc.close();
	}
}
