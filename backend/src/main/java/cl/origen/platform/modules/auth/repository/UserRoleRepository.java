package cl.origen.platform.modules.auth.repository;

import cl.origen.platform.modules.auth.entity.UserRole;
import cl.origen.platform.modules.auth.entity.UserRoleId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository
        extends JpaRepository<UserRole, UserRoleId> {
}
