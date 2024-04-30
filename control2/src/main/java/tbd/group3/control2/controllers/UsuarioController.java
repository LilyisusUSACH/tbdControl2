package tbd.group3.control2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tbd.group3.control2.entities.UsuarioEntity;
import tbd.group3.control2.services.UsuarioService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @GetMapping("")
    public ResponseEntity<List<UsuarioEntity>> getAllUsers(){
      return ResponseEntity.ok(usuarioService.getUsuarios());
    }
    @GetMapping("/{usuario_id}/completed")
    public ResponseEntity<?> getTareasCompletadas(@RequestParam("usuario_id") Long usuario_id){
        return ResponseEntity.ok(usuarioService.getMyCompletedTareas(usuario_id));
    }

    @GetMapping("/{usuario_id}/uncompleted")
    public ResponseEntity<?> getTareasNoCompletadas(@RequestParam("usuario_id") Long usuario_id){
        return ResponseEntity.ok(usuarioService.getMyUncompletedTareas(usuario_id));
    }

    @GetMapping("/{usuario_id}/all")
    public ResponseEntity<?> getAllTareasUser(@RequestParam("usuario_id") Long usuario_id){
        return ResponseEntity.ok(usuarioService.getAllTareasByUser(usuario_id));
    }

}
