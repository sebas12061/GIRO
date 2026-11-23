package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record DetalleCierreRequest(
    @NotNull UUID productoId,
    @NotNull @DecimalMin("0.000") BigDecimal cantidadQueda,
    @NotNull UUID unidadId,
    String observacion
) {
}
