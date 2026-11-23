package com.giro.backend.seguridad;

import jakarta.validation.constraints.NotBlank;

public record CambiarPasswordRequest(
    @NotBlank String password
) {
}
