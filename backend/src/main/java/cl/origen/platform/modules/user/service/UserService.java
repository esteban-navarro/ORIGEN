package cl.origen.platform.modules.user.service;

import cl.origen.platform.common.constants.ApiMessages;
import cl.origen.platform.common.exception.ConflictException;
import cl.origen.platform.common.exception.ResourceNotFoundException;
import cl.origen.platform.modules.auth.entity.Role;
import cl.origen.platform.modules.auth.entity.UserRole;
import cl.origen.platform.modules.auth.entity.UserRoleId;
import cl.origen.platform.modules.auth.repository.RoleRepository;
import cl.origen.platform.modules.auth.repository.UserRoleRepository;
import cl.origen.platform.modules.user.dto.request.CreateUserRequest;
import cl.origen.platform.modules.user.dto.request.UpdateUserRequest;
import cl.origen.platform.modules.user.dto.response.UserResponse;
import cl.origen.platform.modules.user.entity.User;
import cl.origen.platform.modules.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Transactional
    public UserResponse create(CreateUserRequest request) {

        if (userRepository.existsByUsernameIgnoreCase(request.getUsername())) {
            throw new ConflictException(ApiMessages.USERNAME_ALREADY_EXISTS);
        }

        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new ConflictException(ApiMessages.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEnabled(true);

        User savedUser = userRepository.save(user);

        Role userRole = roleRepository.findByNameIgnoreCase("USER")
            .orElseThrow(() -> new IllegalStateException(ApiMessages.USER_ROLE_NOT_FOUND));

        UserRoleId userRoleId = new UserRoleId(
                savedUser.getId(),
                userRole.getId()
        );

        UserRole roleAssignment = new UserRole();
        roleAssignment.setUserRoleId(userRoleId);
        roleAssignment.setUser(savedUser);
        roleAssignment.setRole(userRole);

        userRoleRepository.save(roleAssignment);

        return toResponse(savedUser);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public UserResponse findById(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiMessages.USER_NOT_FOUND));

        return toResponse(user);
    }

    public UserResponse update(UUID id, UpdateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiMessages.USER_NOT_FOUND));


        if (userRepository.existsByUsernameIgnoreCaseAndIdNot(request.getUsername(), id)) {
            throw new ConflictException(ApiMessages.USERNAME_ALREADY_EXISTS);
        }

         if (userRepository.existsByEmailIgnoreCaseAndIdNot(request.getEmail(), id)) {
            throw new ConflictException(ApiMessages.EMAIL_ALREADY_EXISTS);
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        User updatedUser = userRepository.save(user);

        return toResponse(updatedUser);
    }

    public void delete(UUID id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiMessages.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    private UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.isEnabled(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
