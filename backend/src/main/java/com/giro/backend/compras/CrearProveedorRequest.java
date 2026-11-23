package com.giro.backend.compras;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProveedorRequest(
    @NotNull UUID restauranteId,
    @NotBlank String name,
    String identificacion,
    String telefono,
    String direccion
) {
}
