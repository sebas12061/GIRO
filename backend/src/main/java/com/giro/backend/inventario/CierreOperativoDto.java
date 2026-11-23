package com.giro.backend.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CierreOperativoDto(
    UUID id,
    UUID restauranteId,
    LocalDate fecha,
    UUID usuarioId,
    CierreOperativoStatus status,
    List<DetalleCierreOperativoDto> detalles
) {
}
