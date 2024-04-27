package tbd.group3.control2.repositories;


import tbd.group3.control2.entities.RolEntity;

import java.util.List;

public interface RolRepository {
    List<RolEntity> findAll();

    List<RolEntity> findAllByUsuario(Long id_usuario);
    RolEntity create(RolEntity rol, String actualUser);
    RolEntity findById(Long id);

    List<RolEntity> findRolEntitiesByRoleEnumIn(List<String> roleNames);

    RolEntity update(RolEntity rol, String actualUser);
    Boolean delete(Long id, String actualUser);

    RolEntity createByUser(RolEntity rol, Long id_usuario);
}
