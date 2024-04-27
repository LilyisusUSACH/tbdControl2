package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.Rol_PermisosEntity;

import java.util.List;

import static tbd.group3.control2.repositories.TareaRepositoryImpl.deleteSql;

@Repository
public class Rol_PermisosRepositoryImpl implements Rol_PermisosRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<Rol_PermisosEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM rol_permisos";
            return connection.createQuery(query).executeAndFetch(Rol_PermisosEntity.class);
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
    public Rol_PermisosEntity findById(Long id) {
        String sqlQuery = "SELECT * FROM rol_permiso WHERE id = :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Rol_PermisosEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public Rol_PermisosEntity create(Rol_PermisosEntity rol_permisos, String actualUser) {
        String sqlInsertQuery = "INSERT INTO rol_permiso(id_permiso, id_rol) VALUES(:id_permiso, :id_rol)";
        try (Connection con = sql2o.open()){
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(rol_permisos).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }


    @Override
    public Rol_PermisosEntity update(Rol_PermisosEntity rol_permisos, String actualUser) {
        String sqlUpdateQuery = "UPDATE rol_permiso SET id_permiso= :id_permiso, id_rol = :id_rol WHERE id = :id";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("id_permiso", rol_permisos.getId_permiso())
                    .addParameter("id_rol", rol_permisos.getId_rol())
                    .addParameter("id", rol_permisos.getId())
                    .executeUpdate();
            return rol_permisos;
        } catch (Exception e) {
            System.out.println("Error al actualizar el rol_permiso: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM rol_permiso WHERE id = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
