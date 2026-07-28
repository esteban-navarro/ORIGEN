package cl.origen.platform.modules.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;
import java.util.UUID;

@Getter
@Builder
public class AuthenticatedUserResponse {

    private final UUID id;

    private final String username;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final Set<String> roles;

    private final Set<String> permissions;

}
