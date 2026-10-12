package com.giro.backend.productos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearProductoRequest(@NotNull UUID restauranteId, @NotBlank String name, @NotNull @DecimalMin("0.00") BigDecimal price, @NotNull UUID categoriaId, @NotNull UUID unidadId) { }
