package com.revature.utils;

import java.security.Key;

import com.revature.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtFactory {
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	public static String jwsStringFactory(User user) {
		
		return Jwts.builder()
				.setSubject("authentication")
				.claim("user_id", Integer.toString(user.getUser_id()))
				.claim("role", user.getRole())
				.signWith(key)
				.compact()
				;
	}
	
	public static String parseJwtBody(String jwtCookieValue,String field) {
		try {
			Jws<Claims> jwsClaims = Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(jwtCookieValue);
			if (jwsClaims.getBody() != null) {
				String fieldValue = (String) jwsClaims.getBody().get(field);
				return fieldValue;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "invalid";
	}
}
