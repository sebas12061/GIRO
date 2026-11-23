package com.giro.backend.pedidos;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearPedidoRequest(
    @NotNull UUID restauranteId,
    @NotNull @Positive Long numero,
    UUID mesaId,
    UUID clienteId,
    @NotNull TipoServicio tipoServicio,
    String observaciones,
    @NotEmpty @Valid List<ItemPedidoRequest> detalles
) {
}
