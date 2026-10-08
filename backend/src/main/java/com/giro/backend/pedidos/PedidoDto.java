package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PedidoDto(
    UUID id,
    UUID restauranteId,
    Long numero,
    PedidoEstado estado,
    BigDecimal total,
    OffsetDateTime fechaHora
) {
}
