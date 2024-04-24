package tbd.group3.control2.controllers.DTO;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username","message","jwt","status"})
public record AuthResponse(String username,
                           String message,
                           String jwt,
                           boolean status) {
}
