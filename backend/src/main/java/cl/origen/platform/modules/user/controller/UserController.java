package cl.origen.platform.modules.user.controller;

import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.response.Response;
import cl.origen.platform.common.response.ResponseFactory;
import cl.origen.platform.modules.user.dto.request.CreateUserRequest;
import cl.origen.platform.modules.user.dto.request.UpdateUserRequest;
import cl.origen.platform.modules.user.dto.response.UserResponse;
import cl.origen.platform.modules.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "User management endpoints."
)
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('USER_CREATE')")
    @PostMapping
    public ResponseEntity<Response<UserResponse>> create(
        @RequestBody @Valid CreateUserRequest request) {

            UserResponse response = userService.create(request);

            return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseFactory.ok(ApiMessages.USER_CREATED, response));
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping
    public ResponseEntity<Response<List<UserResponse>>> findAll() {

        List<UserResponse> response = userService.findAll();

        return ResponseEntity.ok(
                ResponseFactory.ok(ApiMessages.USERS_RETRIEVED, response)
        );
    }

    @PreAuthorize("hasAuthority('USER_READ')")
    @GetMapping("/{id}")
    public ResponseEntity<Response<UserResponse>> findById(
            @PathVariable UUID id) {

        UserResponse response = userService.findById(id);

        return ResponseEntity.ok(
                ResponseFactory.ok(ApiMessages.USER_RETRIEVED, response)
        );
    }

    @PreAuthorize("hasAuthority('USER_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<Response<UserResponse>> update(
            @PathVariable UUID id,
            @RequestBody @Valid UpdateUserRequest request) {

        UserResponse response = userService.update(id, request);

        return ResponseEntity.ok(
                ResponseFactory.ok(ApiMessages.USER_UPDATED, response)
        );
    }

    @PreAuthorize("hasAuthority('USER_DELETE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> delete(
            @PathVariable UUID id) {

        userService.delete(id);

        return ResponseEntity.ok(
            ResponseFactory.ok(ApiMessages.USER_DELETED, null)
        );
    }

}
