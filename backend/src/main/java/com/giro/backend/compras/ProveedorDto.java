package com.giro.backend.compras;

import java.util.UUID;

public record ProveedorDto(
    UUID id,
    UUID restauranteId,
    String name,
    String identificacion,
    String telefono,
    String direccion,
    ProveedorStatus status
) {
}
