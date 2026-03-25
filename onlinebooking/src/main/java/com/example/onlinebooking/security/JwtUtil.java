// package com.example.onlinebooking.security;
// import java.util.Date;

// import javax.crypto.SecretKey;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Component;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.security.Keys;



// @Component
// public class JwtUtil {

//     @Value("${jwt.secret}")
//     private String secret;

//     @Value("${jwt.expiration}")
//     private long expiration;

//     public String generateToken(String email, String name) {

  
//     SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

//     return Jwts.builder()

//             .setSubject(email)
//             .claim("email", email)
//             .claim("name", name)
//             .setIssuedAt(new Date())
//             .setExpiration(new Date(System.currentTimeMillis() + expiration))
//             .signWith(key, SignatureAlgorithm.HS256)
//             .compact();
// }

//         public String extractUsername(String token) {
//             return extractAllClaims(token).getSubject();
//         }

//         public boolean validateToken(String token) {
//             try {
//                 extractAllClaims(token);
//                 return true;
//             } catch (Exception e) {
//                 return false;
//             }
//         }

//         Claims extractAllClaims(String token) {
//             SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
//             return Jwts.parserBuilder()
//                     .setSigningKey(key)
//                     .build()
//                     .parseClaimsJws(token)
//                     .getBody();
//         }


// }

