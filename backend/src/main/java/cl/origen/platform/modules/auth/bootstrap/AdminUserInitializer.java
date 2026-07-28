package cl.origen.platform.modules.auth.bootstrap;

import cl.origen.platform.config.properties.BootstrapProperties;
import cl.origen.platform.modules.auth.entity.Role;
import cl.origen.platform.modules.auth.entity.User;
import cl.origen.platform.modules.auth.entity.UserRole;
import cl.origen.platform.modules.auth.entity.UserRoleId;
import cl.origen.platform.modules.auth.repository.RoleRepository;
import cl.origen.platform.modules.auth.repository.UserRepository;
import cl.origen.platform.modules.auth.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initializes the bootstrap administrator defined in the application
 * configuration.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private static final String ADMIN_ROLE = "ADMIN";

    private final BootstrapProperties bootstrapProperties;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(final ApplicationArguments args) {

        if (!bootstrapProperties.isEnabled()) {

            log.info("Bootstrap administrator is disabled.");

            return;

        }

        final BootstrapProperties.Admin admin =
                bootstrapProperties.getAdmin();

        if (userRepository.existsByUsernameIgnoreCase(admin.getUsername())) {

            log.info(
                    "Bootstrap administrator '{}' already exists.",
                    admin.getUsername());

            return;

        }

        final Role adminRole = roleRepository
                .findByNameIgnoreCase(ADMIN_ROLE)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Role '%s' not found."
                                        .formatted(ADMIN_ROLE)));

        log.info(
                "Creating bootstrap administrator '{}'.",
                admin.getUsername());

        final User user = new User();

        user.setUsername(admin.getUsername());
        user.setEmail(admin.getEmail());
        user.setPassword(passwordEncoder.encode(admin.getPassword()));
        user.setFirstName(admin.getFirstName());
        user.setLastName(admin.getLastName());
        user.setEnabled(true);

        /*
         * Persist the user first to obtain the generated UUID.
         */
        final User savedUser = userRepository.save(user);

        final UserRole userRole = new UserRole();

        userRole.setUserRoleId(
                new UserRoleId(
                        savedUser.getId(),
                        adminRole.getId()
                )
        );

        userRole.setUser(savedUser);
        userRole.setRole(adminRole);

        userRoleRepository.save(userRole);

        log.info(
                "Bootstrap administrator '{}' created successfully.",
                savedUser.getUsername());

    }

}
