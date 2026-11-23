package com.giro.backend.fiados;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RegistrarMovimientoFiadoRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID fiadoId,
    @NotNull TipoMovimientoFiado tipoMovimiento,
    @NotNull @DecimalMin("0.01") BigDecimal valor,
    UUID ventaId,
    String observacion
) {
}
