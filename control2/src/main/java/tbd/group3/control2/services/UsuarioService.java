package tbd.group3.control2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbd.group3.control2.entities.TareaEntity;
import tbd.group3.control2.entities.UsuarioEntity;
import tbd.group3.control2.repositories.UsuarioRepository;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> getUsuarios(){
        return usuarioRepository.findAll();
    }
    public List<TareaEntity> getMyCompletedTareas(Long id_usuario, String actualUser){
       return  usuarioRepository.getMyTareas(id_usuario, actualUser);
    }

    public List<TareaEntity> getMyUncompletedTareas(Long id_usuario, String actualUser){
        return  usuarioRepository.getMyUncompletedTareas(id_usuario,actualUser);
    }

    public List<TareaEntity> getAllTareasByUser(Long id_usuario, String actualUser){
        return usuarioRepository.getMyTareas(id_usuario, actualUser);
    }
}
