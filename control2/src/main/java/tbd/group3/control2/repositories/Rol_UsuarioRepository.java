package tbd.group3.control2.repositories;

import tbd.group3.control2.entities.Rol_UsuarioEntity;

import java.util.List;

public interface Rol_UsuarioRepository {
    List<Rol_UsuarioEntity> findAll();
    Rol_UsuarioEntity create(Rol_UsuarioEntity rol_usuario, String actualUser);
    Rol_UsuarioEntity findById(Long id);
    Rol_UsuarioEntity update(Rol_UsuarioEntity rol_usuario, String actualUser);
    Boolean delete(Long id, String actualUser);
}


