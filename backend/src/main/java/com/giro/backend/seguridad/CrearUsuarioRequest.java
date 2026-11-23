package com.giro.backend.seguridad;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CrearUsuarioRequest(
    @NotNull UUID restauranteId,
    UUID empleadoId,
    @NotBlank String username,
    @NotBlank String password,
    @NotEmpty Set<UUID> roleIds
) {
}
