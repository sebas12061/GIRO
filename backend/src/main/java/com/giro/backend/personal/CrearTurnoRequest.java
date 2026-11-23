package com.giro.backend.personal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record CrearTurnoRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID empleadoId,
    @NotNull LocalDate fecha,
    @NotNull TipoTurno tipo,
    @NotNull LocalTime horaInicio,
    @NotNull LocalTime horaFin,
    @NotNull @DecimalMin("0.00") BigDecimal valor
) {
}
