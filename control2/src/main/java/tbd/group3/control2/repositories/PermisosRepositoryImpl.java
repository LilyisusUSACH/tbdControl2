package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.PermisosEntity;

import java.util.List;

import static tbd.group3.control2.repositories.TareaRepositoryImpl.deleteSql;

@Repository
public class PermisosRepositoryImpl implements PermisosRepository{

    @Autowired
    private Sql2o sql2o;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<PermisosEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM permisos";
            return connection.createQuery(query).executeAndFetch(PermisosEntity.class);
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
    public PermisosEntity findById(Long id_permisos) {
        String sqlQuery = "SELECT * FROM permisos WHERE id_permisos= :id_permisos";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id_permisos", id_permisos)
                    .executeAndFetchFirst(PermisosEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public PermisosEntity create(PermisosEntity permisos, String actualUser) {
        String sqlInsertQuery = "INSERT INTO permisos(nombre) VALUES(:nombre)";
        try (Connection con = sql2o.open()){
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(permisos).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }


    @Override
    public PermisosEntity update(PermisosEntity permisos, String actualUser) {
        String sqlUpdateQuery = "UPDATE permisos SET nombre= :nombre WHERE id_permisos = :id_permisos";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("nombre", permisos.getNombre())
                    .addParameter("id_permisos", permisos.getId_permisos())
                    .executeUpdate();
            return permisos;
        } catch (Exception e) {
            System.out.println("Error al actualizar el permiso: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM permisos WHERE id_permisos = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
