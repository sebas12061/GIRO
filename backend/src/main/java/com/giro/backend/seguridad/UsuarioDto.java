package com.giro.backend.seguridad;

import java.util.Set;
import java.util.UUID;

public record UsuarioDto(
    UUID id,
    UUID restauranteId,
    UUID empleadoId,
    String username,
    boolean activo,
    Set<String> roles
) {
}
