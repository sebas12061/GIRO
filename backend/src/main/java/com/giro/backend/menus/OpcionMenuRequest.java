package com.giro.backend.menus;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record OpcionMenuRequest(
    UUID productoId,
    UUID categoriaId,
    @NotBlank String name,
    @NotNull @DecimalMin("0.00") BigDecimal price,
    @NotNull @PositiveOrZero Integer orden
) {
}
