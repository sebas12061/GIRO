package com.giro.backend.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CerrarInventarioRequest(
    @NotNull UUID restauranteId,
    @NotNull LocalDate fecha,
    UUID usuarioId,
    @NotEmpty @Valid List<DetalleCierreRequest> detalles
) {
}
