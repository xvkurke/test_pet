package dev.lynxie.webapi.user.repository;

import dev.lynxie.webapi.user.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(UserRole.RoleName role);
}
