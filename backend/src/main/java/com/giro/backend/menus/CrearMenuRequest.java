package com.giro.backend.menus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearMenuRequest(
    @NotNull UUID restauranteId,
    @NotBlank String name,
    LocalDate fechaInicio,
    LocalDate fechaFin,
    @Valid List<OpcionMenuRequest> opciones
) {
}
