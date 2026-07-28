package cl.origen.platform.modules.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.response.Response;
import cl.origen.platform.common.response.ResponseFactory;
import cl.origen.platform.modules.auth.dto.LoginRequest;
import cl.origen.platform.modules.auth.dto.LoginResponse;
import cl.origen.platform.modules.auth.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * REST controller responsible for authentication.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(
        name = "Authentication",
        description = "Authentication and authorization endpoints."
)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Authenticates a user and returns a JWT.
     *
     * @param request login request.
     * @return authenticated user and JWT.
     */
    @Operation(
            operationId = "login",
            summary = "Authenticate user",
            description = "Authenticates a user and returns a JWT token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials",
                    content = @Content
            )
    })
    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(
            @Valid @RequestBody final LoginRequest request) {

        final LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(
                ResponseFactory.ok(
                        ApiMessages.LOGIN_SUCCESS,
                        response
                )
        );
    }

}
