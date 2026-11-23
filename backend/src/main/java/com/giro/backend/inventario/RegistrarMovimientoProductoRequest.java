package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RegistrarMovimientoProductoRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID productoId,
    @NotNull UUID unidadId,
    @NotNull LocalDate fecha,
    @NotNull TipoMovimientoProducto tipoMovimiento,
    @NotNull @DecimalMin("0.001") BigDecimal cantidad,
    String referencia,
    UUID usuarioId,
    String observacion
) {
}
