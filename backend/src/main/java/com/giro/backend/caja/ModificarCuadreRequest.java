package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record ModificarCuadreRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID cuadreId,
    @NotNull @DecimalMin("0.00") BigDecimal efectivoReal,
    @NotNull UUID usuarioAdministradorId
) {
}
