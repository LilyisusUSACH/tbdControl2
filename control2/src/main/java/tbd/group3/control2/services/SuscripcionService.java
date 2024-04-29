package tbd.group3.control2.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tbd.group3.control2.entities.SuscripcionEntity;
import tbd.group3.control2.repositories.SuscripcionRepository;

import java.util.List;

@Service
public class SuscripcionService {

    @Autowired
    SuscripcionRepository suscripcionRepository;

    public SuscripcionEntity createSuscripcion(SuscripcionEntity suscripcion) {
        suscripcionRepository.save(suscripcion);
        return suscripcion;
    }

    public SuscripcionEntity updateSuscripcion(SuscripcionEntity suscripcion) {
        suscripcionRepository.update(suscripcion);
        return suscripcion;
    }

    public SuscripcionEntity getSuscripcionById(Long id) {
        return suscripcionRepository.findById(id);
    }

    public List<SuscripcionEntity> getAllSuscripciones() {
        return suscripcionRepository.findAll();
    }

    public void deleteSuscripcion(Long id) {
        suscripcionRepository.delete(id);
    }
}
