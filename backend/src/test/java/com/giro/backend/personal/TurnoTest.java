package com.giro.backend.personal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class TurnoTest {

    @Test
    void turnoProgresaDeProgramadoAFinalizado() {
        Turno turno = new Turno(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), TipoTurno.MANANA,
            LocalTime.of(8, 0), LocalTime.of(12, 0), new BigDecimal("50000.00"));

        turno.iniciar();
        turno.finalizar();

        assertEquals(TurnoStatus.FINALIZADO, turno.getStatus());
    }

    @Test
    void turnoCanceladoNoPuedeFinalizarse() {
        Turno turno = new Turno(UUID.randomUUID(), UUID.randomUUID(), LocalDate.now(), TipoTurno.NOCHE,
            LocalTime.of(18, 0), LocalTime.of(22, 0), new BigDecimal("60000.00"));
        turno.cancelar();

        assertThrows(IllegalStateException.class, turno::finalizar);
    }
}
