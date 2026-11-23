package com.giro.backend.seguridad;

import jakarta.validation.constraints.NotBlank;

public record CrearPermisoRequest(
    @NotBlank String name
) {
}
