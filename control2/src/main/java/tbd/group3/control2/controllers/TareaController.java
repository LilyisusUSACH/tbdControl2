package tbd.group3.control2.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbd.group3.control2.entities.TareaEntity;
import tbd.group3.control2.services.TareaService;
import tbd.group3.control2.services.UsuarioService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/tarea")
public class TareaController {


    @Autowired
    TareaService tareaService;
    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<?> getAllTareas(
            @RequestHeader(value = "Authorization", required = false) String token
    ){

        String actualUser = usuarioService.getUser(token);

        List<TareaEntity> tareas = tareaService.getAllTareas();

        return ResponseEntity.ok(tareas);
    }

    @GetMapping("/id")
    public ResponseEntity<?> getTareaById(
            @RequestParam(value = "id") Long id,
            @RequestHeader(value = "Authorization", required = false) String token
    ){
        String actualUser= usuarioService.getUser(token);
        return ResponseEntity.ok(tareaService.getTareaByID(id));
    }

    @PostMapping("")
    public ResponseEntity<?> postTarea(
            @RequestBody TareaEntity tarea,
            @RequestHeader(value = "Authorization",required = false) String token) {

        String actualUser= usuarioService.getUser(token);

        TareaEntity tareaEntity = tareaService.createTarea(tarea,actualUser);
        if (tareaEntity!=null)
            return ResponseEntity.ok(tareaEntity);
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("")
    public ResponseEntity<TareaEntity> updateTarea(
            @RequestBody TareaEntity tarea,
            @RequestHeader(value = "Authorization",required = false) String token) {

        String actualUser = usuarioService.getUser(token);
        TareaEntity updatedTarea = tareaService.updateTarea(tarea,actualUser);
        if (updatedTarea != null) {
            return ResponseEntity.ok(updatedTarea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteTarea(
            @RequestParam("id") Long id,
            @RequestHeader(value = "Authorization",required = false) String token) {
        String actualUser= usuarioService.getUser(token);
        if(tareaService.deleteTarea(id,actualUser)) {
            return ResponseEntity.ok("deleted");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/usuario")
    public ResponseEntity<?> getTareasByUsuario(
            @RequestParam("id_usuario") Long id_usuario,
            @RequestHeader(value = "Authorization",required = false) String token){
        String actualUser= usuarioService.getUser(token);
        return ResponseEntity.ok(tareaService.getTareaByID(id_usuario));
    }

    @GetMapping("/{search}")
    public ResponseEntity<?> getTaskCoincidence(
            @RequestParam("search") String search,
            @RequestHeader(value = "Authorization",required = false) String token){
        String actualUser=usuarioService.getUser(token);
        return ResponseEntity.ok(tareaService.getCoincidenceTasks(search,actualUser));
    }
}
