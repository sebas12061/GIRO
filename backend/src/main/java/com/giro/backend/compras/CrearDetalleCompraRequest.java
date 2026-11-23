package com.giro.backend.compras;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearDetalleCompraRequest(
    @NotNull UUID productoId,
    @NotNull @Positive BigDecimal cantidad,
    @NotNull @DecimalMin("0.01") BigDecimal valorUnitario
) {
}
