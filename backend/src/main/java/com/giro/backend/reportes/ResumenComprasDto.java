package com.giro.backend.reportes;

import java.math.BigDecimal;

public record ResumenComprasDto(
    long numeroCompras,
    BigDecimal totalCompras
) {
}
