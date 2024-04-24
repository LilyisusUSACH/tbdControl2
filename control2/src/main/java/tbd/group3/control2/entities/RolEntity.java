package tbd.group3.control2.entities;

import lombok.*;

@Data
public class RolEntity {
    private Long id;
    private RolType nombre; // Cambiamos el tipo de String a RolType
    // Enum que define los tipos de roles
    public enum RolType {
        ADMIN, USER
    }
}
