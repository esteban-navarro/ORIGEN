package cl.origen.platform.config.security;

import cl.origen.platform.modules.auth.security.AuthenticationUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityBeansConfig {

    /**
     * Creates the application's password encoder.
     *
     * @return password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();

    }

    /**
     * Creates the authentication provider used by Spring Security.
     *
     * @param userDetailsService user details service
     * @param passwordEncoder password encoder
     * @return DAO authentication provider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            final AuthenticationUserDetailsService userDetailsService,
            final PasswordEncoder passwordEncoder) {

        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder);

        return provider;

    }

    /**
     * Creates the application's authentication manager.
     *
     * @param configuration authentication configuration
     * @return authentication manager
     * @throws Exception if the authentication manager cannot be created
     */
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();

    }

}
