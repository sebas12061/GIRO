package com.giro.backend.pedidos;

import java.util.UUID;

public record PedidoAuditEvent(
    UUID restauranteId,
    UUID pedidoId,
    String accion,
    String estadoAnterior,
    String estadoNuevo
) {
}
