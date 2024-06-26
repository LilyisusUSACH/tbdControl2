package tbd.group3.control2.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbd.group3.control2.entities.TareaEntity;
import tbd.group3.control2.entities.UsuarioEntity;
import tbd.group3.control2.repositories.UsuarioRepository;
import tbd.group3.control2.utils.JwtUtils;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<UsuarioEntity> getUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioEntity getUsuario(String username){
        return usuarioRepository.findByUsername(username).get();
    }

    public List<TareaEntity> getMyCompletedTareas(Long id_usuario){
       return  usuarioRepository.getMyTareas(id_usuario);
    }

    public List<TareaEntity> getMyUncompletedTareas(Long id_usuario){
        return  usuarioRepository.getMyUncompletedTareas(id_usuario);
    }

    public List<TareaEntity> getAllTareasByUser(Long id_usuario){
        return usuarioRepository.getMyTareas(id_usuario);
    }

/*
    public String getUser(String token){
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            if (jwtUtils.validateToken(jwtToken)) {
                return jwtUtils.extractUsername(jwtToken);
            }
        }
        return null;
    }

 */

    public String getUser(String token){
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
            if (decodedJWT != null) {
                return jwtUtils.extractUsername(decodedJWT);
            }
        }
        return null;
    }

}
