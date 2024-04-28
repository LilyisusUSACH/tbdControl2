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


}
