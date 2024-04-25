package tbd.group3.control2.repositories;

import tbd.group3.control2.entities.Rol_PermisosEntity;

import java.util.List;

public interface Rol_PermisosRepository {
    List<Rol_PermisosEntity> findAll();
    Rol_PermisosEntity create(Rol_PermisosEntity rol_permisos, String actualUser);
    Rol_PermisosEntity findById(Long id);
    Rol_PermisosEntity update(Rol_PermisosEntity rol_permisos, String actualUser);
    Boolean delete(Long id, String actualUser);
}
