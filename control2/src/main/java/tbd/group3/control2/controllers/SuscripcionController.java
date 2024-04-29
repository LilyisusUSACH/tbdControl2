package tbd.group3.control2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tbd.group3.control2.entities.SuscripcionEntity;
import tbd.group3.control2.services.SuscripcionService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/suscripciones")
public class SuscripcionController {

    @Autowired
    SuscripcionService suscripcionService;

    @PostMapping
    public ResponseEntity<SuscripcionEntity> createSuscripcion(@RequestBody SuscripcionEntity suscripcion) {
        SuscripcionEntity createdSuscripcion = suscripcionService.createSuscripcion(suscripcion);
        return new ResponseEntity<>(createdSuscripcion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionEntity> updateSuscripcion(@PathVariable Long id, @RequestBody SuscripcionEntity suscripcion) {
        SuscripcionEntity currentSuscripcion = suscripcionService.getSuscripcionById(id);
        if (currentSuscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        suscripcion.setId(id);
        suscripcionService.updateSuscripcion(suscripcion);
        return new ResponseEntity<>(suscripcion, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionEntity> getSuscripcionById(@PathVariable Long id) {
        SuscripcionEntity suscripcion = suscripcionService.getSuscripcionById(id);
        if (suscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(suscripcion, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SuscripcionEntity>> getAllSuscripciones() {
        List<SuscripcionEntity> suscripciones = suscripcionService.getAllSuscripciones();
        return new ResponseEntity<>(suscripciones, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSuscripcion(@PathVariable Long id) {
        SuscripcionEntity suscripcion = suscripcionService.getSuscripcionById(id);
        if (suscripcion == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        suscripcionService.deleteSuscripcion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
