package tbd.group3.control2.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import tbd.group3.control2.entities.SuscripcionEntity;
import tbd.group3.control2.entities.TareaEntity;

import java.util.List;

@Repository
public class SuscripcionRepositoryImpl implements SuscripcionRepository{

    @Autowired
    private Sql2o sql2o;

    public SuscripcionRepositoryImpl(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public List<SuscripcionEntity> findAll() {
        try (Connection connection = sql2o.open()) {
            String query = "SELECT * FROM suscripcion";
            return connection.createQuery(query).executeAndFetch(SuscripcionEntity.class);
        }
    }

    @Override
    public SuscripcionEntity findById(Long id) {
        String sql = "SELECT * FROM suscripciones WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            return conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(SuscripcionEntity.class);
        }
    }
    @Override
    public SuscripcionEntity save(SuscripcionEntity suscripcion) {
        String sql = "INSERT INTO suscripciones (usuarioId, endpoint, p256dhKey, authKey) " +
                "VALUES (:usuarioId, :endpoint, :p256dhKey, :authKey)";
        try (Connection conn = sql2o.beginTransaction()) {
            Long id = (Long) conn.createQuery(sql, true)
                    .bind(suscripcion)
                    .executeUpdate()
                    .getKey();
            suscripcion.setId(id);
            conn.commit();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }

        return null;
    }

    public SuscripcionEntity update(SuscripcionEntity suscripcion) {
        String sql = "UPDATE suscripciones SET usuarioId = :usuarioId, endpoint = :endpoint, " +
                "p256dhKey = :p256dhKey, authKey = :authKey WHERE id = :id";
        try (Connection conn = sql2o.beginTransaction()) {
            conn.createQuery(sql)
                    .bind(suscripcion)
                    .executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
        return null;
    }
    @Override
    public SuscripcionEntity delete(Long id) {
        String sql = "DELETE FROM suscripciones WHERE id = :id";
        try (Connection conn = sql2o.open()) {
            conn.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
        return null;
    }


}
