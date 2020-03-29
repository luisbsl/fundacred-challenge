package br.com.fundacred.challenge.helper;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author luisbsl
 *
 */
@Service
public final class JWTTokenHelper {
	
	private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(JWTTokenHelper.class);

	private JWTTokenHelper() {
	}

	public static Key getSigningKey() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(hashSHA256(EnviromentHelper.Key.FUNDACRED_CHALLENGE_API_SECRET.getVal()));
		return new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	}
	
	public static String generateUserToken(final User user) {
		var now = ZonedDateTime.now();
		var after30Min = now.plus(Duration.ofMinutes(30));
		
		return Jwts.builder()
					.setId(EnviromentHelper.Key.FUNDACRED_CHALLENGE_JWT_ID.getVal())
		            .setIssuedAt(Date.from(now.toInstant()))
		            .setExpiration(Date.from(after30Min.toInstant()))
		            .setSubject(user.getName())
		            .signWith(getSigningKey())
		            .compact();
	}
	
	public static Jws<Claims> decodeToken(final String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey())
				.build().parseClaimsJws(token);
	}
	
	private static String hashSHA256(final String str) {
		StringBuilder sb = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hashInBytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
	        
	        for (byte b : hashInBytes) {
	            sb.append(String.format("%02x", b));
	        }
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("Fatal error", e);
		}
        return sb.toString();
	}

}
