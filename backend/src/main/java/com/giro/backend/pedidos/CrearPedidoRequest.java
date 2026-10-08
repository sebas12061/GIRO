package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearPedidoRequest(
    @NotNull UUID restauranteId,
    @NotNull @Positive Long numero,
    @NotNull BigDecimal total
) {
}
