package cl.origen.platform.modules.auth.security;

import cl.origen.platform.modules.auth.entity.Permission;
import cl.origen.platform.modules.auth.entity.Role;
import cl.origen.platform.modules.auth.entity.RolePermission;
import cl.origen.platform.modules.auth.entity.User;
import cl.origen.platform.modules.auth.entity.UserRole;
import cl.origen.platform.modules.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads a user by username and maps it to Spring Security's
     * {@link UserDetails} implementation.
     *
     * @param username username used during authentication
     * @return authenticated user details
     * @throws UsernameNotFoundException if the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(final String username) {

        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: "));

        return AuthenticationUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .enabled(user.isEnabled())
                .roles(mapRoles(user))
                .permissions(mapPermissions(user))
                .authorities(mapAuthorities(user))
                .build();

    }

    /**
     * Maps the user's roles and permissions to Spring Security authorities.
     *
     * @param user authenticated user
     * @return granted authorities
     */
    private Collection<GrantedAuthority> mapAuthorities(final User user) {

        Set<GrantedAuthority> authorities = new HashSet<>();

        for (UserRole userRole : user.getUserRoles()) {

            Role role = userRole.getRole();

            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + role.getName()));

            for (RolePermission rolePermission : role.getRolePermissions()) {

                Permission permission = rolePermission.getPermission();

                authorities.add(
                        new SimpleGrantedAuthority(
                                permission.getName()));

            }

        }

        return authorities;

    }

    /**
     * Extracts the user's role names.
     *
     * @param user authenticated user
     * @return role names
     */
    private Set<String> mapRoles(final User user) {

        Set<String> roles = new HashSet<>();

        for (UserRole userRole : user.getUserRoles()) {

            Role role = userRole.getRole();

            roles.add(role.getName());

        }

        return roles;

    }

    /**
     * Extracts the user's permission names.
     *
     * @param user authenticated user
     * @return permission names
     */
    private Set<String> mapPermissions(final User user) {

        Set<String> permissions = new HashSet<>();

        for (UserRole userRole : user.getUserRoles()) {

            Role role = userRole.getRole();

            for (RolePermission rolePermission : role.getRolePermissions()) {

                Permission permission = rolePermission.getPermission();

                permissions.add(permission.getName());

            }

        }

        return permissions;

    }

}
