package br.com.fundacred.challenge.util;

import org.mindrot.jbcrypt.BCrypt;

public interface BCryptHelper {

	public static String generateHash(final String str) {
		return BCrypt.hashpw(str, BCrypt.gensalt(5));
	}

	public static boolean checkHash(final String str, final String strEncryted) {
		return BCrypt.checkpw(str, strEncryted);
	}

}
