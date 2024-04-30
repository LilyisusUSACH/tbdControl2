package tbd.group3.control2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tbd.group3.control2.entities.TareaEntity;

import java.util.List;

public interface TareaRepository {

    List<TareaEntity> findAll();
    TareaEntity create(TareaEntity tarea);
    TareaEntity findById(Long id);
    TareaEntity update(TareaEntity tarea);

    Boolean delete(Long id, String actualUser);

List<TareaEntity> findByUser(Long id_usuario);
    List<TareaEntity> getCoincidences(String search);
}
