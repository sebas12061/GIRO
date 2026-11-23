package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MovimientoProductoDto(
    UUID id,
    UUID restauranteId,
    UUID productoId,
    UUID unidadId,
    LocalDate fecha,
    TipoMovimientoProducto tipoMovimiento,
    BigDecimal cantidad,
    String referencia,
    UUID usuarioId,
    String observacion,
    OffsetDateTime fechaHora
) {
}
