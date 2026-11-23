package com.giro.backend.personal;

import java.util.UUID;

public record EmpleadoDto(
    UUID id,
    UUID restauranteId,
    String name,
    String documento,
    String telefono,
    EmpleadoStatus status
) {
}
