package cl.origen.platform.modules.auth.security;

import lombok.Builder;
import lombok.Getter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.UUID;
import java.util.Set;

@Getter
@Builder
public class AuthenticationUserDetails implements UserDetails {

    private final UUID id;

    private final String username;

    private final String password;

    private final String email;

    private final String firstName;

    private final String lastName;

    private final boolean enabled;

    private final Set<String> roles;

    private final Set<String> permissions;

    private final Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

}
