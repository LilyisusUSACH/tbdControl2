package tbd.group3.control2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
public class TareaEntity {
    private Long id;
    private String titulo;
    private String descripcion;
    private Date expira;
    private Boolean completado;
}

