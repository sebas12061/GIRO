package com.giro.backend.compras;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record CompraDto(
    UUID id,
    UUID restauranteId,
    UUID proveedorId,
    UUID cajaId,
    OffsetDateTime fecha,
    BigDecimal total,
    CompraStatus status,
    String observacion,
    List<DetalleCompraDto> detalles
) {
}
