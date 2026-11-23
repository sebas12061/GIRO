package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.util.UUID;

public record DetallePedidoDto(
    UUID id,
    UUID productoId,
    UUID opcionMenuId,
    BigDecimal cantidad,
    BigDecimal precioUnitario,
    BigDecimal subtotal,
    String observaciones,
    DetallePedidoEstado estado
) {
}
