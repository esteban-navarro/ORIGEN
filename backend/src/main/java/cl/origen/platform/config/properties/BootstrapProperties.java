package cl.origen.platform.config.properties;

import lombok.Getter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Bootstrap configuration properties.
 */
@Getter
@ConfigurationProperties(prefix = "security.bootstrap")
public class BootstrapProperties {

    private final boolean enabled;

    private final Admin admin;

    public BootstrapProperties(
            final boolean enabled,
            final Admin admin) {

        this.enabled = enabled;
        this.admin = admin;

    }

    /**
     * Bootstrap administrator configuration.
     */
    @Getter
    public static class Admin {

        private final String username;

        private final String password;

        private final String email;

        private final String firstName;

        private final String lastName;

        public Admin(
                final String username,
                final String password,
                final String email,
                final String firstName,
                final String lastName) {

            this.username = username;
            this.password = password;
            this.email = email;
            this.firstName = firstName;
            this.lastName = lastName;

        }

    }

}
