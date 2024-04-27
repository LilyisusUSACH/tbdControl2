package tbd.group3.control2.entities;
import lombok.*;

@Data
@Builder
// TODO: PASAR A BUSQUEDAS DE DB
public class RolEntity {
    private Long id;
    private ERoles nombre;
}
