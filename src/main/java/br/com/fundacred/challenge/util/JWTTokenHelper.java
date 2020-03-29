package br.com.fundacred.challenge.util;

import java.security.Key;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import br.com.fundacred.challenge.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public final class JWTTokenHelper {

	private JWTTokenHelper() {
	}

	public static Key getSigningKey() {
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(BCryptHelper.generateHash(EnviromentHelper.Key.FUNDACRED_CHALLENGE_API_SECRET.getVal()));
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
	
	public static Jws<Claims> decodeUserToken(final String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
	}

}
