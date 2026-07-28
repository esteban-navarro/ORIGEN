package cl.origen.platform.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cl.origen.platform.config.properties.ApplicationProperties;
import cl.origen.platform.config.properties.BootstrapProperties;
import cl.origen.platform.config.properties.JwtProperties;

/**
 * Registers application configuration properties.
 */
@Configuration
@EnableConfigurationProperties({
    ApplicationProperties.class,
    JwtProperties.class,
    BootstrapProperties.class
})
public class ConfigurationPropertiesConfig {
}
