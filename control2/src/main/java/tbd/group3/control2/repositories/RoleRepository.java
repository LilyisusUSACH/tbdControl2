package tbd.group3.control2.repositories;

import org.springframework.data.repository.CrudRepository;
import tbd.group3.control2.entities.RolEntity;
import tbd.group3.control2.entities.PermissionEntity;

import java.util.List;

public interface RoleRepository extends CrudRepository<RolEntity, Long> {
    List<RolEntity> findRolEntitiesByRoleEnumIn(List<String> roleNames);

    List<RolEntity> getPermissionList();
}
