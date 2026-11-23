package com.giro.backend.reportes;

import java.math.BigDecimal;

public record ResumenFiadosDto(
    long cuentasAbiertas,
    long cuentasPagadas,
    long cuentasVencidas,
    BigDecimal saldoPendiente
) {
}
