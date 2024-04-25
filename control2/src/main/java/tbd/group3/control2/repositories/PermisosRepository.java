package tbd.group3.control2.repositories;

import tbd.group3.control2.entities.PermisosEntity;

import java.util.List;

public interface PermisosRepository {

    List<PermisosEntity> findAll();
    PermisosEntity create(PermisosEntity permisos, String actualUser);
    PermisosEntity findById(Long id);
    PermisosEntity update(PermisosEntity permisos, String actualUser);
    Boolean delete(Long id, String actualUser);
}
