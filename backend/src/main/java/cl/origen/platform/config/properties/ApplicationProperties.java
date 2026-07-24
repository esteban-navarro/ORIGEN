package cl.origen.platform.config.properties;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * Application-specific configuration properties.
 *
 * <p>
 * Centralizes custom configuration defined under the {@code application}
 * namespace in {@code application.yml}. Using immutable configuration
 * objects improves maintainability, type safety and prevents accidental
 * modification at runtime.
 * </p>
 */
@Getter
@RequiredArgsConstructor
@Validated
@ConfigurationProperties(
    prefix = "application",
    ignoreUnknownFields = false
)
public class ApplicationProperties {

    /**
     * Display name of the application.
     */
    @NotBlank
    private final String name;

    /**
     * Current application version.
     */
    @NotBlank
    private final String version;

    /**
     * Short application description.
     */
    @NotBlank
    private final String description;

}