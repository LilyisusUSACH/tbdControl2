package tbd.group3.control2.entities;
import jakarta.persistence.*;
import lombok.*;
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
    private Long id;
    private RolType nombre; // Cambiamos el tipo de String a RolType
    // Enum que define los tipos de roles
    public enum RolType {
        ADMIN, USER
    }
}
