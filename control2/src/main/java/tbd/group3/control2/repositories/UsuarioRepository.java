package tbd.group3.control2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sql2o.Connection;
import tbd.group3.control2.entities.UsuarioEntity;

import java.util.List;

public interface UsuarioRepository{

    List<UsuarioEntity> findAll();

    UsuarioEntity findById(Long id);

    UsuarioEntity findByUsername(String username);

    Boolean Login(String username, String Password);

    UsuarioEntity create(UsuarioEntity usuario, String actualUser);

    UsuarioEntity update(UsuarioEntity usuario, String actualUser);

    void setUsername(String username, Connection connection);

    Boolean delete(Long id, String actualUser);

}
