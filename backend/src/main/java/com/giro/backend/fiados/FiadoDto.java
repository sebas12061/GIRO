package com.giro.backend.fiados;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record FiadoDto(
    UUID id,
    UUID restauranteId,
    UUID clienteId,
    TipoFiado tipo,
    LocalDate fechaInicio,
    LocalDate fechaVencimiento,
    BigDecimal saldo,
    FiadoStatus status,
    OffsetDateTime fechaHora,
    List<MovimientoFiadoDto> movimientos
) {
}
