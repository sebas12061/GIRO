package com.giro.backend.pagos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record RegistrarPagoRequest(@NotNull UUID restauranteId, @NotNull UUID ventaId, @NotNull TipoPago tipo, @NotNull @DecimalMin("0.01") BigDecimal valor, String referencia) { }
