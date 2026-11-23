package com.giro.backend.pedidos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemPedidoRequest(
    @NotNull UUID productoId,
    UUID opcionMenuId,
    @NotNull @Positive java.math.BigDecimal cantidad,
    String observaciones
) {
}
