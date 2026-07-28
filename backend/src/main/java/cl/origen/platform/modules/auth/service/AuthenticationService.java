package cl.origen.platform.modules.auth.service;

import cl.origen.platform.config.properties.JwtProperties;
import cl.origen.platform.modules.auth.dto.AuthenticatedUserResponse;
import cl.origen.platform.modules.auth.dto.LoginRequest;
import cl.origen.platform.modules.auth.dto.LoginResponse;
import cl.origen.platform.modules.auth.security.AuthenticationUserDetails;
import cl.origen.platform.modules.auth.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final JwtProperties jwtProperties;

    /**
     * Authenticates a user and generates a JWT.
     *
     * @param request login request
     * @return authenticated user information and access token
     */
    public LoginResponse login(final LoginRequest request) {

        final UsernamePasswordAuthenticationToken authenticationToken =
                UsernamePasswordAuthenticationToken.unauthenticated(
                        request.getUsername(),
                        request.getPassword());

        final Authentication authentication =
                authenticationManager.authenticate(authenticationToken);

        final AuthenticationUserDetails user = (AuthenticationUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateToken(user);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .tokenType(TOKEN_TYPE)
                .expiresIn(jwtProperties.getExpiration().toSeconds())
                .user(buildAuthenticatedUser(user))
                .build();

    }

    /**
     * Builds the authenticated user response.
     *
     * @param user authenticated user
     * @return authenticated user DTO
     */
    private AuthenticatedUserResponse buildAuthenticatedUser(
            final AuthenticationUserDetails user) {

        return AuthenticatedUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getRoles())
                .permissions(user.getPermissions())
                .build();

    }

}
