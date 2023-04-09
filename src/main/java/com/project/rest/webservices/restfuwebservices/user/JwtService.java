package com.project.rest.webservices.restfuwebservices.user;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		
		return createToken(claims,username);
	}

	private String createToken(Map<String, Object> claims, String username) {
		//we will add some dependency in pom.xml for jwt token creation
		return Jwts.builder().setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
				.signWith(getSignKey(),SignatureAlgorithm.HS256).compact();
				
	}

	private Key getSignKey() {
		byte[] keyByte = Decoders.BASE64.decode("472D4B6150645367566B58703273357638792F423F4528482B4D625165546857");
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims,T> claimsResolver) {
		// TODO Auto-generated method stub
		
		final Claims claim = extractAllClaims(token);
		return claimsResolver.apply(claim);
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		
		return extractExpirationDate(token).before(new Date());
	}
	
}
