package com.giro.backend.pedidos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CrearMesaRequest(
    @NotNull UUID restauranteId,
    @NotNull @Positive Integer numero,
    @NotNull @Positive Integer capacidad
) {
}
