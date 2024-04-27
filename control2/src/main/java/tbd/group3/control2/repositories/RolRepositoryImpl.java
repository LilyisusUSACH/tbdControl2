package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.RolEntity;

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    public List<RolEntity> findAllByUsuario(Long id_usuario){
        String sqlQuery = "SELECT rol.* FROM rol INNER JOIN public.rol_usuario ru on rol.id = ru.id_rol WHERE id_usuario = :idUsuario";
        try (Connection con = sql2o.open()) {
            return con.createQuery(sqlQuery)
                    .addParameter("idUsuario", id_usuario)
                    .executeAndFetch(RolEntity.class);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public RolEntity findById(Long id_rol) {
        String sqlQuery = "SELECT * FROM rol WHERE id= :id_rol";
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
        String sqlSelectQuery = "SELECT 1 FROM rol WHERE nombre = :nombre";
        String sqlInsertQuery = "INSERT INTO rol(nombre) VALUES(:nombre)";
        try (Connection con = sql2o.open()){
            Long idExistente = con.createQuery(sqlSelectQuery).addParameter("nombre", rol.getNombre()).executeScalar(Long.class);
            if(idExistente != null) return findById(idExistente);
            usuarioRepository.setUsername(actualUser, con);
            Long id = con.createQuery(sqlInsertQuery).bind(rol).executeUpdate().getKey(Long.class);
            return findById(id);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    @Override
    public RolEntity createByUser(RolEntity rol, Long id_usuario){
        String selectRolQuery = "SELECT id FROM rol WHERE nombre = :nombre";

        String sqlInsertQuery = "INSERT INTO rol(nombre) VALUES(:nombre)";

        String insertRolUsuarioQuery = "INSERT INTO rol_usuario (id_usuario, id_rol) " +
                "SELECT :idUsuario, :idRol WHERE NOT " +
                "exists( SELECT 1 FROM rol_usuario WHERE id_usuario = :idUsuario and id_rol = :idRol)";

        try (Connection con = sql2o.beginTransaction()){
            // Verificar si el rol ya existe
            Long idRol = con.createQuery(selectRolQuery)
                    .addParameter("nombre", rol.getNombre())
                    .executeScalar(Long.class);

            // Si el rol no existe, crearlo
            if (idRol == null) {
                idRol = con.createQuery(sqlInsertQuery, true)
                        .addParameter("nombre", rol.getNombre())
                        .executeUpdate()
                        .getKey(Long.class);
            }

            con.createQuery(insertRolUsuarioQuery)
                    .addParameter("idUsuario", id_usuario)
                    .addParameter("idRol", idRol)
                    .executeUpdate();

            con.commit();
            return findById(idRol);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            //e.printStackTrace();
            //con.rollback();
            return null;
        }
    }

    @Override
    public List<RolEntity> findRolEntitiesByRoleEnumIn(List<String> roleNames) {
        try (Connection con = sql2o.open()) {
            String sql = "SELECT * FROM rol";
            List<RolEntity> roles = con.createQuery(sql)
                    .executeAndFetch(RolEntity.class);
            return roles.stream()
                    .filter(role -> roleNames.contains(role.getNombre().name()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Manejar cualquier excepción que pueda ocurrir
            //e.printStackTrace();
            return null;
        }
    }

    @Override
    public RolEntity update(RolEntity rol, String actualUser) {
        String sqlUpdateQuery = "UPDATE rol SET nombre= :nombre WHERE id = :id_rol";
        try (Connection con = sql2o.open()) {
            usuarioRepository.setUsername(actualUser, con);
            con.createQuery(sqlUpdateQuery)
                    .addParameter("nombre", rol.getNombre())
                    .addParameter("id_rol", rol.getId())
                    .executeUpdate();
            return rol;
        } catch (Exception e) {
            System.out.println("Error al actualizar el rol: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Boolean delete(Long id, String actualUser) {
        String sqlDeleteQuery = "DELETE FROM rol WHERE id = :id";
        return deleteSql(id, actualUser, sqlDeleteQuery, sql2o, usuarioRepository);
    }

}
