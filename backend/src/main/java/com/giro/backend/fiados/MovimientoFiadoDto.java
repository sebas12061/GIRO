package com.giro.backend.fiados;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record MovimientoFiadoDto(
    UUID id,
    UUID ventaId,
    TipoMovimientoFiado tipoMovimiento,
    BigDecimal valor,
    OffsetDateTime fechaHora,
    String observacion
) {
}
