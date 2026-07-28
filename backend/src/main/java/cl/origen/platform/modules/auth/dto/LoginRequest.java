package cl.origen.platform.modules.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequest {

    @Schema(
            description = "Username used to authenticate.",
            example = "admin"
    )
    @NotBlank
    private final String username;

    @Schema(
            description = "User password.",
            example = "Password123!"
    )
    @NotBlank
    private final String password;

}
