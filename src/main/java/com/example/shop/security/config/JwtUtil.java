// package com.example.shop.security.config;

// import java.security.KeyFactory;
// import java.security.NoSuchAlgorithmException;
// import java.security.interfaces.RSAPrivateKey;
// import java.security.interfaces.RSAPublicKey;
// import java.security.spec.InvalidKeySpecException;
// import java.security.spec.PKCS8EncodedKeySpec;
// import java.security.spec.X509EncodedKeySpec;
// import java.util.Base64;
// import java.util.Collection;
// import java.util.Date;
// import java.util.List;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.stereotype.Service;

// import com.auth0.jwt.JWT;
// import com.auth0.jwt.JWTVerifier;
// import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.interfaces.DecodedJWT;
// import com.auth0.jwt.interfaces.Verification;
// import com.example.shop.user.User;

// import io.jsonwebtoken.security.InvalidKeyException;



// @Service
// @Configuration
// public class JwtUtil {
//     private RSAPrivateKey privateKey;
//     private RSAPublicKey publicKey;
//     private final Algorithm algorithm;
//     private final RsaProperties rsaProperties;

//     JwtUtil(RsaProperties rsaProperties){
//         this.rsaProperties = rsaProperties;
//         loadKeys();
//         algorithm = Algorithm.RSA256(publicKey, privateKey);
//     }

//     public String createToken(final Authentication auth){
//         Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();

//         List<String> roles = authorities.stream().map(authority -> authority.getAuthority().replace("ROLE_","")).toList();

//         return JWT.create()
//                 .withSubject(((User) auth.getPrincipal()).getUsername())
//                 .withExpiresAt(new Date(new Date().getTime() + 1000000))
//                 .withIssuedAt(new Date())
//                 .withClaim("roles", roles)
//                 .sign(algorithm);
//     }

//     public String getUsername(final String token){
//         final Verification verification = JWT.require(algorithm);
//         final JWTVerifier verifier = verification.build();
//         final DecodedJWT decodedJWT = verifier.verify(token);
//         return decodedJWT.getSubject();
//     }

//     public Date getExpiresAt(final String token){
//         final Verification verification = JWT.require(algorithm);
//         final JWTVerifier verifier = verification.build();
//         final DecodedJWT decodedJWT = verifier.verify(token);
//         return decodedJWT.getExpiresAt();
//     }

//     public Boolean validate(final String tokenAsString){
//         try {
//             final Verification verification = JWT.require(algorithm);
//             final JWTVerifier verifier = verification.build();
//             verifier.verify(tokenAsString);
//             return true;
//         } catch (final Exception e) {

//             return false;
//         }
//     }

//     private void loadKeys() {
//         try {
//             KeyFactory keyFactory = KeyFactory.getInstance("RSA");
//             PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(rsaProperties.getPrivateKey()));
//             privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

//             X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(rsaProperties.getPublicKey()));
//             publicKey = (RSAPublicKey) keyFactory.generatePublic(publicKeySpec);
//         } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//             throw new RuntimeException(e);
//         }
//     }
// }
