package com.giro.backend.fiados;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record CrearFiadoRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID clienteId,
    @NotNull TipoFiado tipo,
    LocalDate fechaVencimiento,
    UUID ventaId
) {
}
