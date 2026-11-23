package com.giro.backend.reportes;

import java.math.BigDecimal;

public record ResumenVentasDto(
    long numeroVentas,
    BigDecimal totalVentas,
    BigDecimal efectivo,
    BigDecimal transferencias,
    BigDecimal fiados
) {
}
