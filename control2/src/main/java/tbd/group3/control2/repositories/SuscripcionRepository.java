package tbd.group3.control2.repositories;

import tbd.group3.control2.entities.SuscripcionEntity;

import java.util.List;

public interface SuscripcionRepository {
    List<SuscripcionEntity> findAll();
    SuscripcionEntity findById(Long id);
    SuscripcionEntity save(SuscripcionEntity suscripcion);
    SuscripcionEntity update(SuscripcionEntity suscripcion);
    SuscripcionEntity delete(Long id);

}
