package com.giro.backend.caja;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CuadreDto(
    UUID id,
    UUID cajaId,
    BigDecimal base,
    BigDecimal ventasEfectivo,
    BigDecimal transferencias,
    BigDecimal fiados,
    BigDecimal gastos,
    BigDecimal efectivoEsperado,
    BigDecimal efectivoReal,
    BigDecimal diferencia,
    UUID usuarioId,
    OffsetDateTime fechaHora,
    CuadreStatus status
) {
}
