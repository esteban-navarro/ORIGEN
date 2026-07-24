package cl.origen.platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures the application's security.
 *
 * <p>
 * This configuration is prepared to evolve to JWT authentication.
 * Public endpoints are explicitly declared, while every other endpoint
 * requires authentication.
 * </p>
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Public endpoints that do not require authentication.
     */
    private static final String[] PUBLIC_ENDPOINTS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/actuator/health",
            "/api/v1/status"
    };

    /**
     * Configures the Spring Security filter chain.
     *
     * @param http HttpSecurity configuration.
     * @return configured SecurityFilterChain.
     * @throws Exception if a security configuration error occurs.
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                // REST API
                .csrf(csrf -> csrf.disable())

                // JWT will be stateless
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Public endpoints
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())

                // Disable default login page
                .formLogin(form -> form.disable())

                // Disable HTTP Basic authentication
                .httpBasic(httpBasic -> httpBasic.disable());

        return http.build();
    }

}
