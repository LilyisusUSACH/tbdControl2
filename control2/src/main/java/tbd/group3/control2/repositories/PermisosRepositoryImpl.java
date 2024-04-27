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
            String query = "SELECT * FROM permiso";
            return connection.createQuery(query).executeAndFetch(PermisosEntity.class);
        }
    }

    @Override
    public List<PermisosEntity> findAllByRol(Long id_rol){
        String sqlQuery = "SELECT permiso.* FROM permiso INNER JOIN public.rol_permiso rp on permiso.id = rp.id_permiso WHERE id_rol = :idRol";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("idRol", id_rol)
                    .executeAndFetch(PermisosEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public PermisosEntity findById(Long id) {
        String sqlQuery = "SELECT * FROM permiso WHERE id= :id";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("id", id)
                    .executeAndFetchFirst(PermisosEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public PermisosEntity createPermisoByRol(PermisosEntity permisos, Long id_rol){
        String selectRolQuery = "SELECT id FROM permiso WHERE nombre = :nombre";

        String sqlInsertQuery = "INSERT INTO permiso(nombre) VALUES(:nombre)";

        String insertRolUsuarioQuery = "INSERT INTO rol_permiso (id_permiso, id_rol)  " +
                "SELECT :idPermiso, :idRol WHERE NOT " +
                "exists( SELECT 1 FROM rol_permiso WHERE id_permiso = :idPermiso and id_rol = :idRol)";

        try (Connection con = sql2o.beginTransaction()){
            // Verificar si el rol ya existe
            Long idPermiso = con.createQuery(selectRolQuery)
                    .addParameter("nombre", permisos.getNombre())
                    .executeScalar(Long.class);

            // Si el rol no existe, crearlo
            if (idPermiso == null) {
                idPermiso = con.createQuery(sqlInsertQuery, true)
                        .addParameter("nombre", permisos.getNombre())
                        .executeUpdate()
                        .getKey(Long.class);
            }

            con.createQuery(insertRolUsuarioQuery)
                    .addParameter("idPermiso", idPermiso)
                    .addParameter("idRol", id_rol)
                    .executeUpdate();

            con.commit();
            return findById(idPermiso);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            //e.printStackTrace();
            //con.rollback();
            return null;
        }
    }

    @Override
    public PermisosEntity create(PermisosEntity permisos, String actualUser) {
        String sqlSelectQuery = "SELECT 1 FROM permiso WHERE nombre = :nombre";
        String sqlInsertQuery = "INSERT INTO permiso(nombre) VALUES(:nombre)";

        try (Connection con = sql2o.open()){

            Long idExistente = con.createQuery(sqlSelectQuery).addParameter("nombre", permisos.getNombre()).executeScalar(Long.class);

            if(idExistente != null) return findById(idExistente);

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
        String sqlUpdateQuery = "UPDATE permiso SET nombre= :nombre WHERE id = :id";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("nombre", permisos.getNombre())
                    .addParameter("id", permisos.getId())
                    .executeUpdate();
            return permisos;
        } catch (Exception e) {
            System.out.println("Error al actualizar el permiso: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM permiso WHERE id = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
