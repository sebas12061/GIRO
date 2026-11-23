package com.giro.backend.seguridad;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;

public record CrearRolRequest(
    @NotBlank String name,
    Set<UUID> permisoIds
) {
}
