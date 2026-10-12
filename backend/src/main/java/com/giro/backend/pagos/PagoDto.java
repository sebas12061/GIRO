package com.giro.backend.pagos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record PagoDto(UUID id, UUID restauranteId, UUID ventaId, TipoPago tipo, BigDecimal valor, String referencia, PagoStatus status, OffsetDateTime fechaHora) { }
