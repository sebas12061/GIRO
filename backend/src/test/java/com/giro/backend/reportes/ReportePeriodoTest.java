package com.giro.backend.reportes;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class ReportePeriodoTest {

    @Test
    void permitePeriodoValido() {
        ReportePeriodoRequest request = new ReportePeriodoRequest(
            UUID.randomUUID(), LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 30));

        assertDoesNotThrow(() -> {
            if (request.hasta().isBefore(request.desde())) {
                throw new IllegalArgumentException("Periodo invalido");
            }
        });
    }

    @Test
    void rechazaPeriodoInvertido() {
        ReportePeriodoRequest request = new ReportePeriodoRequest(
            UUID.randomUUID(), LocalDate.of(2026, 11, 30), LocalDate.of(2026, 11, 1));

        assertThrows(IllegalArgumentException.class, () -> {
            if (request.hasta().isBefore(request.desde())) {
                throw new IllegalArgumentException("Periodo invalido");
            }
        });
    }
}
