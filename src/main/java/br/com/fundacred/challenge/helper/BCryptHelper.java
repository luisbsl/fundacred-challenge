package br.com.fundacred.challenge.helper;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * @author luisbsl
 *
 */
public interface BCryptHelper {

	public static String generateHash(final String str) {
		return BCrypt.hashpw(str, BCrypt.gensalt(5));
	}

	public static boolean checkHash(final String str, final String strEncryted) {
		return BCrypt.checkpw(str, strEncryted);
	}

}
