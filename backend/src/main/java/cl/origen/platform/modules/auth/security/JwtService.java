package cl.origen.platform.modules.auth.security;

import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cl.origen.platform.config.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Service responsible for generating and validating JSON Web Tokens (JWT).
 */
@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    /**
     * Secret key used to sign and validate JWT tokens.
     */
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {

        this.jwtProperties = jwtProperties;

        byte[] keyBytes =
                Decoders.BASE64.decode(jwtProperties.getSecret());

        this.signingKey = Keys.hmacShaKeyFor(keyBytes);

    }

    /**
     * Generates a JWT token for the authenticated user.
     *
     * @param userDetails authenticated user
     * @return signed JWT token
     */
    public String generateToken(UserDetails userDetails) {

        Instant issuedAt = Instant.now();

        Instant expiration = issuedAt.plus(jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuer(jwtProperties.getIssuer())
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(signingKey)
                .compact();

    }

    /**
     * Extracts the username (subject) from the JWT.
     *
     * @param token JWT token
     * @return username
     */
    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);

    }

    /**
     * Extracts a claim from the JWT.
     *
     * @param token JWT token
     * @param claimsResolver claim resolver
     * @return extracted claim
     */
    public <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    /**
     * Validates the JWT token.
     *
     * @param token JWT token
     * @param userDetails authenticated user
     * @return true if the token is valid
     */
    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        String username = extractUsername(token);

        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);

    }

    /**
     * Extracts all claims contained in the JWT.
     *
     * @param token JWT token
     * @return JWT claims
     */
    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

    }

    /**
     * Returns the JWT expiration instant.
     *
     * @param token JWT token
     * @return expiration instant
     */
    private Instant extractExpiration(String token) {

        return extractClaim(token, Claims::getExpiration)
                .toInstant();

    }

    /**
     * Indicates whether the JWT has expired.
     *
     * @param token JWT token
     * @return true if the token has expired
     */
    private boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .isBefore(Instant.now());

    }

}
