package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.RolEntity;

import java.util.List;

import static tbd.group3.control2.repositories.TareaRepositoryImpl.deleteSql;

@Repository
public class RolRepositoryImpl implements RolRepository{

    @Autowired
    private Sql2o sql2o;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<RolEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM rol";
            return connection.createQuery(query).executeAndFetch(RolEntity.class);
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
    public RolEntity findById(Long id_rol) {
        String sqlQuery = "SELECT * FROM rol WHERE id_rol= :id_rol";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id_rol", id_rol)
                    .executeAndFetchFirst(RolEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public RolEntity create(RolEntity rol, String actualUser) {
        String sqlInsertQuery = "INSERT INTO rol(nombre) VALUES(:nombre)";
        try (Connection con = sql2o.open()){
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(rol).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }


    @Override
    public RolEntity update(RolEntity rol, String actualUser) {
        String sqlUpdateQuery = "UPDATE rol SET nombre= :nombre WHERE id_rol = :id_rol";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("nombre", rol.getNombre())
                    .addParameter("id_rol", rol.getId_rol())
                    .executeUpdate();
            return rol;
        } catch (Exception e) {
            System.out.println("Error al actualizar el rol: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM rol WHERE id_rol = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
