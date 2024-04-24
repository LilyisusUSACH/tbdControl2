package tbd.group3.control2.controllers.DTO;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(@NotBlank String username,
                           @NotBlank String password) {
}
