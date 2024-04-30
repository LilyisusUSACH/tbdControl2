package tbd.group3.control2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbd.group3.control2.entities.TareaEntity;
import tbd.group3.control2.repositories.TareaRepository;

import java.util.List;

// TODO: implementar CRUD
@Service
public class TareaService {

    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    UsuarioService usuarioService;

    public List<TareaEntity> getAllTareas(){
        return tareaRepository.findAll();
    }

    public List<TareaEntity> getTareasByUsuario(Long id_usuario){ return tareaRepository.findByUser(id_usuario); }


    public TareaEntity getTareaByID(Long id){
        return tareaRepository.findById(id);
    }

    public TareaEntity createTarea(TareaEntity tarea, String actualUser) {
        tarea.setId_usuario( usuarioService.getUsuario(actualUser).getId());
        return tareaRepository.create(tarea);
    }

    public TareaEntity updateTarea(TareaEntity tarea, String actualUser) { return tareaRepository.update(tarea);}


    public Boolean deleteTarea(Long id,String actualUser){
        return tareaRepository.delete(id,actualUser);
    }

    public List<TareaEntity> getCoincidenceTasks(String search, String actualUser){
        return tareaRepository.getCoincidences(search);
    }
}
