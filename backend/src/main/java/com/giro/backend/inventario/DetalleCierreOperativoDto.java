package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.util.UUID;

public record DetalleCierreOperativoDto(
    UUID id,
    UUID productoId,
    BigDecimal cantidadQueda,
    UUID unidadId,
    String observacion
) {
}
