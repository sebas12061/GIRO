package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExistenciaDto(
    UUID productoId,
    UUID unidadId,
    LocalDate fecha,
    BigDecimal amanece,
    BigDecimal llega,
    BigDecimal factDesayuno,
    BigDecimal factAlmuerzo,
    BigDecimal consumoPersonal,
    BigDecimal gastado,
    BigDecimal ajuste,
    BigDecimal existenciaCalculada
) {
}
