package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegistrarMovimientoRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID cajaId,
    @NotNull TipoMovimientoCaja tipo,
    @NotBlank String concepto,
    @NotNull @DecimalMin("0.01") BigDecimal valor,
    String origen,
    UUID usuarioId
) {
}
