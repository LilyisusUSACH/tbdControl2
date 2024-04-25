package tbd.group3.control2.entities;

import lombok.Data;

@Data
public class PermisosEntity {
    private Long id_permisos;
    private Permiso nombre;

    public enum Permiso {
        READ, WRITE, UPDATE
    }
}