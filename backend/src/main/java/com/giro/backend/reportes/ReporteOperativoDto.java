package com.giro.backend.reportes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ReporteOperativoDto(
    UUID restauranteId,
    LocalDate desde,
    LocalDate hasta,
    ResumenVentasDto ventas,
    ResumenComprasDto compras,
    ResumenFiadosDto fiados,
    long cuadresCerrados,
    BigDecimal diferenciaCaja,
    long turnosFinalizados,
    BigDecimal valorTurnos
) {
}
