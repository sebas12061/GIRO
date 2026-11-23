package com.giro.backend.seguridad;

import java.util.Set;
import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record AsignarRolesRequest(
    @NotEmpty Set<UUID> roleIds
) {
}
