package com.tcs.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.tcs.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key signingKey =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60 * 24; 
    

    private static long lastRestartTimestamp = System.currentTimeMillis(); 
    
    public static void setLastRestartTimestamp(long timestamp) {
        lastRestartTimestamp = timestamp;
    }
    
    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole().name());
        claims.put("userId", user.getUserId());
        claims.put("restartTimestamp", lastRestartTimestamp);

        return Jwts.builder()
                .setClaims(claims) 
                .setSubject(user.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME)
                )
                .signWith(signingKey)
                .compact();
    }

    public String extractUsername(String token) {
        return extractClaims(token).getSubject();
    }

//    public boolean isTokenValid(String token) {
//        return !extractClaims(token)
//                .getExpiration()
//                .before(new Date());
//    }
    public boolean isTokenValid(String token) {
        Claims claims = extractClaims(token);
        long tokenRestartTimestamp = claims.get("restartTimestamp", Long.class);
        
        // If the token's restart timestamp is before the current restart timestamp, it's invalid
        if (tokenRestartTimestamp < lastRestartTimestamp) {
            return false;
        }

        // Token expiration check
        Date expiration = claims.getExpiration();
        return !expiration.before(new Date());
    }

    

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build() 
                .parseClaimsJws(token)
                .getBody();
    }
    
    public int extractUserId(String token) {
        Claims claims = extractClaims(token);
        Long userIdLong = claims.get("userId", Long.class);  // Extract userId as Long

       
        if (userIdLong != null && userIdLong <= Integer.MAX_VALUE) {
            return userIdLong.intValue();  
        } else {
            throw new IllegalArgumentException("userId is too large to fit into an int.");
        }
    }
    public String extractRole(String token) {
        Claims claims = extractClaims(token);
        return claims.get("role", String.class); 
    }
}
