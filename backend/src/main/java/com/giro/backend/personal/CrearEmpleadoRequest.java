package com.giro.backend.personal;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearEmpleadoRequest(
    @NotNull UUID restauranteId,
    @NotBlank String name,
    String documento,
    String telefono
) {
}
