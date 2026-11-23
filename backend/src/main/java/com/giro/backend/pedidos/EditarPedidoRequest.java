package com.giro.backend.pedidos;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

public record EditarPedidoRequest(
    @NotEmpty @Valid List<CrearDetallePedidoRequest> detalles
) {
}
