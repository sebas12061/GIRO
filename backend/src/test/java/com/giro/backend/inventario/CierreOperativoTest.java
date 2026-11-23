package com.giro.backend.inventario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CierreOperativoTest {

    @Test
    void cierrePuedeCerrarSeYNoAgregarDetallesDespues() {
        CierreOperativo cierre = new CierreOperativo(UUID.randomUUID(), LocalDate.now(), UUID.randomUUID());
        cierre.agregarDetalle(new DetalleCierreOperativo(UUID.randomUUID(), new BigDecimal("6.000"),
            UUID.randomUUID(), "QUED"));
        cierre.cerrar();

        assertEquals(CierreOperativoStatus.CLOSED, cierre.getStatus());
        assertThrows(IllegalStateException.class,
            () -> cierre.agregarDetalle(new DetalleCierreOperativo(UUID.randomUUID(), BigDecimal.ONE,
                UUID.randomUUID(), null)));
    }
}
