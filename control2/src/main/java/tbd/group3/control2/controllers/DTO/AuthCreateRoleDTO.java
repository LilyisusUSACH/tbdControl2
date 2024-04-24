package tbd.group3.control2.controllers.DTO;

import jakarta.validation.constraints.Size;

import java.util.List;

public record AuthCreateRoleDTO(
        @Size(max = 3, message = "The user cannot have more than 3 roles") List<String> roleListName
) {
}
