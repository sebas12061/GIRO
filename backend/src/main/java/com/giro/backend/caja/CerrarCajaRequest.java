package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CerrarCajaRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID cajaId,
    @NotNull @DecimalMin("0.00") BigDecimal efectivoReal,
    UUID usuarioId
) {
}
