package tbd.group3.control2.repositories;

import tbd.group3.control2.entities.PermisosEntity;

import java.util.List;

public interface PermisosRepository {

    List<PermisosEntity> findAll();

    List<PermisosEntity> findAllByRol(Long id_rol);
    PermisosEntity create(PermisosEntity permisos, String actualUser);

    PermisosEntity createPermisoByRol(PermisosEntity permisos, Long id_rol);
    PermisosEntity findById(Long id);
    PermisosEntity update(PermisosEntity permisos, String actualUser);
    Boolean delete(Long id, String actualUser);
}
