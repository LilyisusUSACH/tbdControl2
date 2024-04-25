package tbd.group3.control2.repositories;


import tbd.group3.control2.entities.RolEntity;

import java.util.List;

public interface RolRepository {
    List<RolEntity> findAll();
    RolEntity create(RolEntity rol, String actualUser);
    RolEntity findById(Long id);
    RolEntity update(RolEntity rol, String actualUser);
    Boolean delete(Long id, String actualUser);
}
