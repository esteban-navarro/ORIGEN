package cl.origen.platform.modules.user.service;

import cl.origen.platform.modules.auth.entity.Role;
import cl.origen.platform.modules.auth.entity.UserRole;
import cl.origen.platform.modules.auth.repository.RoleRepository;
import cl.origen.platform.modules.auth.repository.UserRoleRepository;
import cl.origen.platform.modules.user.dto.request.CreateUserRequest;
import cl.origen.platform.modules.user.dto.request.UpdateUserRequest;
import cl.origen.platform.modules.user.dto.response.UserResponse;
import cl.origen.platform.modules.user.entity.User;
import cl.origen.platform.modules.user.repository.UserRepository;
import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.exception.ConflictException;
import cl.origen.platform.common.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {

        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("john.doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password123");
        request.setFirstName("John");
        request.setLastName("Doe");

        UUID userId = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        Role userRole = new Role();
        userRole.setId(roleId);
        userRole.setName("USER");

        when(userRepository.existsByUsernameIgnoreCase("john.doe"))
                .thenReturn(false);

        when(userRepository.existsByEmailIgnoreCase("john.doe@example.com"))
                .thenReturn(false);

        when(passwordEncoder.encode("Password123"))
                .thenReturn("encoded-password");

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    user.setId(userId);
                    return user;
                });

        when(roleRepository.findByNameIgnoreCase("USER"))
                .thenReturn(Optional.of(userRole));

        // Act
        UserResponse response = userService.create(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(userId);
        assertThat(response.getUsername()).isEqualTo("john.doe");
        assertThat(response.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(response.getFirstName()).isEqualTo("John");
        assertThat(response.getLastName()).isEqualTo("Doe");
        assertThat(response.isEnabled()).isTrue();

        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("Password123");
        verify(roleRepository).findByNameIgnoreCase("USER");
        verify(userRoleRepository).save(any(UserRole.class));
    }

    @Test
    void shouldThrowConflictExceptionWhenUsernameAlreadyExists() {

        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("john.doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password123");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.existsByUsernameIgnoreCase("john.doe"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage(ApiMessages.USERNAME_ALREADY_EXISTS);

        verify(userRepository).existsByUsernameIgnoreCase("john.doe");
        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
        verify(roleRepository, never()).findByNameIgnoreCase(anyString());
        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void shouldThrowConflictExceptionWhenEmailAlreadyExists() {

        // Arrange
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("john.doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("Password123");
        request.setFirstName("John");
        request.setLastName("Doe");

        when(userRepository.existsByUsernameIgnoreCase("john.doe"))
                .thenReturn(false);

        when(userRepository.existsByEmailIgnoreCase("john.doe@example.com"))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.create(request))
                .isInstanceOf(ConflictException.class)
                .hasMessage(ApiMessages.EMAIL_ALREADY_EXISTS);

        verify(userRepository).existsByUsernameIgnoreCase("john.doe");
        verify(userRepository).existsByEmailIgnoreCase("john.doe@example.com");

        verify(userRepository, never()).save(any(User.class));
        verify(passwordEncoder, never()).encode(anyString());
        verify(roleRepository, never()).findByNameIgnoreCase(anyString());
        verify(userRoleRepository, never()).save(any(UserRole.class));
    }

    @Test
    void shouldUpdateUserSuccessfully() {

        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEnabled(true);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("john.updated");
        request.setEmail("john.updated@example.com");
        request.setFirstName("John Updated");
        request.setLastName("Doe Updated");
        request.setEnabled(false);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByUsernameIgnoreCaseAndIdNot(
                "john.updated", userId))
                .thenReturn(false);

        when(userRepository.existsByEmailIgnoreCaseAndIdNot(
                "john.updated@example.com", userId))
                .thenReturn(false);

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        // Act
        UserResponse response = userService.update(userId, request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(userId);
        assertThat(response.getUsername()).isEqualTo("john.updated");
        assertThat(response.getEmail()).isEqualTo("john.updated@example.com");
        assertThat(response.getFirstName()).isEqualTo("John Updated");
        assertThat(response.getLastName()).isEqualTo("Doe Updated");
        assertThat(response.isEnabled()).isFalse();

        verify(userRepository).findById(userId);
        verify(userRepository).existsByUsernameIgnoreCaseAndIdNot("john.updated", userId);
        verify(userRepository).existsByEmailIgnoreCaseAndIdNot("john.updated@example.com", userId);
        verify(userRepository).save(user);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenUpdatingNonExistingUser() {

        // Arrange
        UUID userId = UUID.randomUUID();

        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("john.updated");
        request.setEmail("john.updated@example.com");
        request.setFirstName("John Updated");
        request.setLastName("Doe Updated");
        request.setEnabled(false);

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.update(userId, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApiMessages.USER_NOT_FOUND);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).existsByUsernameIgnoreCaseAndIdNot(anyString(), eq(userId));
        verify(userRepository, never()).existsByEmailIgnoreCaseAndIdNot(anyString(), eq(userId));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldThrowConflictExceptionWhenUpdatingWithExistingUsername() {

        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEnabled(true);

        UpdateUserRequest request = new UpdateUserRequest();
        request.setUsername("existing.user");
        request.setEmail("john.updated@example.com");
        request.setFirstName("John Updated");
        request.setLastName("Doe Updated");
        request.setEnabled(false);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(userRepository.existsByUsernameIgnoreCaseAndIdNot(
                "existing.user", userId))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> userService.update(userId, request))
                .isInstanceOf(ConflictException.class)
                .hasMessage(ApiMessages.USERNAME_ALREADY_EXISTS);

        verify(userRepository).findById(userId);
        verify(userRepository).existsByUsernameIgnoreCaseAndIdNot("existing.user", userId);
        verify(userRepository, never()).existsByEmailIgnoreCaseAndIdNot(anyString(), eq(userId));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldDeleteUserSuccessfully() {

        // Arrange
        UUID userId = UUID.randomUUID();

        User user = new User();
        user.setId(userId);
        user.setUsername("john.doe");
        user.setEmail("john.doe@example.com");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        // Act
        userService.delete(userId);

        // Assert
        verify(userRepository).findById(userId);
        verify(userRepository).delete(user);
    }

    @Test
    void shouldThrowResourceNotFoundExceptionWhenDeletingNonExistingUser() {

        // Arrange
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.delete(userId))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage(ApiMessages.USER_NOT_FOUND);

        verify(userRepository).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

}
