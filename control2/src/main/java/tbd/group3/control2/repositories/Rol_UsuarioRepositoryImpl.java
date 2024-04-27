package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.Rol_UsuarioEntity;
import static tbd.group3.control2.repositories.TareaRepositoryImpl.deleteSql;

import java.util.List;

@Repository
public class Rol_UsuarioRepositoryImpl implements Rol_UsuarioRepository{

    @Autowired
    private Sql2o sql2o;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Rol_UsuarioEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM rol_usuario";
            return connection.createQuery(query).executeAndFetch(Rol_UsuarioEntity.class);
        }
    }
  /*  @Override
    public List<TareaEntity> findAllPagination(int size, int page){
        String sqlQuery = "Select * FROM tarea LIMIT :size OFFSET :offset";
        int offset = (page - 1) * size;
        try(Connection con = sql2o.open()){
            return con.createQuery(sqlQuery).addParameter("size", size)
                    .addParameter("offset",offset).executeAndFetch(TareaEntity.class);
        }catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }*/

    @Override
    public Rol_UsuarioEntity findById(Long id) {
        String sqlQuery = "SELECT * FROM rol_usuario WHERE id= :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id_rol_usuario", id)
                    .executeAndFetchFirst(Rol_UsuarioEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public Rol_UsuarioEntity create(Rol_UsuarioEntity rol_usuario, String actualUser) {
        String sqlInsertQuery = "INSERT INTO rol_usuario(id_usuario, id_rol) VALUES(:id_usuario, :id_rol)";
        try (Connection con = sql2o.open()){
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(rol_usuario).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }


    @Override
    public Rol_UsuarioEntity update(Rol_UsuarioEntity rol_usuario, String actualUser) {
        String sqlUpdateQuery = "UPDATE rol_usuario SET id_usuario= :id_usuario, id_rol = :id_rol WHERE id = :id";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("id_usuario", rol_usuario.getId_usuario())
                    .addParameter("id_rol", rol_usuario.getId_rol())
                    .addParameter("id_rol_usuario", rol_usuario.getId())
                    .executeUpdate();
            return rol_usuario;
        } catch (Exception e) {
            System.out.println("Error al actualizar el rol_usuario: " + e.getMessage());
        }
        return null;
    }


    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM rol_usuario WHERE id_rol_usuario = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
