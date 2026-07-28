package cl.origen.platform.config.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(prefix = "security.jwt")
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
     * JWT expiration.
     */
    private final Duration expiration;

}
