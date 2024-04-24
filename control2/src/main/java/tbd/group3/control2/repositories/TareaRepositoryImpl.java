package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.TareaEntity;

import java.util.List;


@Repository
public class TareaRepositoryImpl implements TareaRepository {

    @Autowired
    private Sql2o sql2o;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<TareaEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM tarea";
            return connection.createQuery(query).executeAndFetch(TareaEntity.class);
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
    public TareaEntity findById(Long id_tarea) {
        String sqlQuery = "SELECT * FROM tarea WHERE id_tarea = :id_tarea";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id_tarea", id_tarea)
                    .executeAndFetchFirst(TareaEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public TareaEntity create(TareaEntity tarea, String actualUser) {
        String sqlInsertQuery = "INSERT INTO tarea(titulo, descripcion, expira, completada) VALUES(:titulo, :descripcion, :expira, :completada)";
        try (Connection con = sql2o.open()){
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(tarea).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }


    @Override
    public TareaEntity update(TareaEntity tarea, String actualUser) {
        String sqlUpdateQuery = "UPDATE tarea SET titulo= :titulo, descripcion = :descripcion, expira = :expira, completada = :completada WHERE id_tarea = :id_tarea";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("descripcion", tarea.getDescripcion())
                    .addParameter("expira", tarea.getExpira())
                    .addParameter("completada", tarea.getCompletado())
                    .addParameter("titulo", tarea.getTitulo())
                    .addParameter("id_tarea", tarea.getId_tarea())
                    .executeUpdate();
            return tarea;
        } catch (Exception e) {
            System.out.println("Error al actualizar la tarea: " + e.getMessage());
        }
        return null;
    }

    /*
    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM tarea WHERE id_tarea = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }
*/

}
