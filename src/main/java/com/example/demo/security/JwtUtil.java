////package com.example.demo.security;
////
////import java.security.KeyStore.Builder;
////import java.util.Date;
////
////import org.springframework.stereotype.Component;
////
////import io.jsonwebtoken.Jwts;
////import io.jsonwebtoken.SignatureAlgorithm;
////
////@Component
////public class JwtUtil{
////	private final String SECRET="MySecretKey123MySecretKey123";
////	
////	public String generateToken(String email) {
////		return Jwts.builder()
////				.setSubject(email)
////				.setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
////                .signWith(SignatureAlgorithm.HS256, SECRET)
////                .compact();
////		
////		
////		
////	}
////	public String extractEmail(String token) {
////        return Jwts.parser()
////                .setSigningKey(SECRET)
////                .parseClaimsJws(token)
////                .getBody()
////                .getSubject();
////    }
////}
//
//
//package com.example.demo.security;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//
//    // SECRET must be at least 256 bits (32 chars)
//    private static final String SECRET = "MySecretKey123MySecretKey123";
//
//    private Key getSigningKey() {
//        return Keys.hmacShaKeyFor(SECRET.getBytes());
//    }
//
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
//                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
//                .compact();
//    }
//
//    public String extractEmail(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(getSigningKey())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//
//        return claims.getSubject();
//    }
//}
//
//
//

package com.example.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 32+ characters = 256+ bits (SAFE)
    private static final String SECRET =
            "MySuperSecureJwtSecretKey1234567890";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email,String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
