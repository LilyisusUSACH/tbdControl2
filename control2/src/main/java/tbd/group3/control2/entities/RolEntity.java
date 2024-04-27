package tbd.group3.control2.entities;
import jakarta.persistence.*;
import lombok.*;

import java.nio.file.FileStore;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles")
public class RolEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rol;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleEnum nombre; // Enum that defines the types of roles available.

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "role_permissions",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    private Set<PermissionEntity> permissions = new HashSet<>();

    public Collection<Object> getPermissionList() {

    }

    public FileStore getRoleEnum() {
        return null;
    }
}