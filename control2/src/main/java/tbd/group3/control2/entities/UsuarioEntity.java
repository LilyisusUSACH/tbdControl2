package tbd.group3.control2.entities;


import lombok.*;

@Data
public class UsuarioEntity {
    private Long id;
    private String username;
    private String password;
    private RolEntity rol;

}
