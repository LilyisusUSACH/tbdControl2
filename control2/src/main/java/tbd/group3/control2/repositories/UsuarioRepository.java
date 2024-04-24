package tbd.group3.control2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tbd.group3.control2.entities.UsuarioEntity;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findUserEntityByUsername(String username);

}
