package com.giro.backend.ventas;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import com.giro.backend.pagos.PagoDto;

public record VentaDto(
    UUID id,
    UUID restauranteId,
    UUID pedidoId,
    BigDecimal subtotal,
    BigDecimal total,
    VentaStatus status,
    OffsetDateTime fechaHora,
    List<PagoDto> pagos
) {
}
