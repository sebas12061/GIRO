package com.giro.backend.personal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record TurnoDto(
    UUID id,
    UUID empleadoId,
    LocalDate fecha,
    TipoTurno tipo,
    LocalTime horaInicio,
    LocalTime horaFin,
    BigDecimal valor,
    TurnoStatus status
) {
}
