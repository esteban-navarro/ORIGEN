package cl.origen.platform.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * JWT configuration properties.
 */
@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT issuer.
     */
    private final String issuer;

    /**
     * JWT secret key.
     */
    private final String secret;

    /**
     * JWT expiration time in minutes.
     */
    private final Long expirationMinutes;

}
