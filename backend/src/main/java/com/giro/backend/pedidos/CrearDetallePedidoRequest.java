package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearDetallePedidoRequest(
    @NotNull UUID productoId,
    UUID opcionMenuId,
    @NotNull @Positive BigDecimal cantidad,
    @NotNull @DecimalMin("0.00") BigDecimal precioUnitario,
    String observaciones
) {
}
