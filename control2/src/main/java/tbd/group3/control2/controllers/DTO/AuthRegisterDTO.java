package tbd.group3.control2.controllers.DTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public record AuthRegisterDTO(
        @NotBlank String username,
        @NotBlank String password,
        @Valid AuthCreateRoleDTO roleRequest
){
}
