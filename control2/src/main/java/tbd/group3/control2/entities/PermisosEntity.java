package tbd.group3.control2.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PermisosEntity {
    private Long id;
    private EPermisos nombre;
}