package walletSystem.authService.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret:a1B2c3D4e5F6g7H8i9J0k1L2m3N4o5P6q7R8s9T0u1V2w3X4y5Z6}")
    private String secretKey;

    @Value("${jwt.expiration:3600000}")
    private long expirationTime;

    // Generate token with username, role, and userId
    public String generateToken(String username, String role, Long userId) {
        return JWT.create()
                .withSubject(username)
                .withClaim("role", role)
                .withClaim("userId", userId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public String extractUsername(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getSubject();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public String extractRole(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getClaim("role").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public Long extractUserId(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getClaim("userId").asLong();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public Date extractExpiration(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            return decodedJWT.getExpiresAt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    private DecodedJWT decodeToken(String token) throws JWTDecodeException {
        return JWT.require(Algorithm.HMAC512(secretKey))
                .build()
                .verify(token);
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expiration = extractExpiration(token);
            return expiration != null && expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public boolean validateToken(String token) {
        try {
            decodeToken(token);
            return !isTokenExpired(token);
        } catch (JWTDecodeException e) {
            return false;
        }
    }
}