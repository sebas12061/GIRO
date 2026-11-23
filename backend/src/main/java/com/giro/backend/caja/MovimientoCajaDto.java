package com.giro.backend.caja;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MovimientoCajaDto(
    UUID id,
    UUID cajaId,
    UUID restauranteId,
    TipoMovimientoCaja tipo,
    String concepto,
    BigDecimal valor,
    String origen,
    UUID usuarioId,
    OffsetDateTime fechaHora
) {
}
