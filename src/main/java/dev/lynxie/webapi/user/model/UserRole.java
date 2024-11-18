package dev.lynxie.webapi.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter
@Setter
@Table(name = "roles", schema = "core_user")
public class UserRole implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName role;

    public String getAuthority() {
        return role.name();
    }

    public enum RoleName {
        ROLE_USER,
        ROLE_ADMIN
    }
}
