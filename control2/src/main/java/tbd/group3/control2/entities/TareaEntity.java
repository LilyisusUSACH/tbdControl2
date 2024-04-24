package tbd.group3.control2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Data
public class TareaEntity {
    private Long id;
    private String title;
    private String description;
    private Date expires;
    private boolean completed;
}
