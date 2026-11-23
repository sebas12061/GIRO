package com.giro.backend.reportes;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record ReportePeriodoRequest(
    @NotNull UUID restauranteId,
    @NotNull LocalDate desde,
    @NotNull LocalDate hasta
) {
}
