package com.giro.backend.compras;

import java.math.BigDecimal;
import java.util.UUID;

public record DetalleCompraDto(
    UUID id,
    UUID productoId,
    BigDecimal cantidad,
    BigDecimal valorUnitario,
    BigDecimal subtotal
) {
}
