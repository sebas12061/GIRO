package com.giro.backend.ventas;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class VentaTest {

    @Test
    void ventaSeMarcaPagadaAlCobrar() {
        Venta venta = new Venta(UUID.randomUUID(), UUID.randomUUID(),
            new BigDecimal("25000.00"), new BigDecimal("25000.00"));

        venta.pagar();

        assertEquals(VentaStatus.PAGADA, venta.getStatus());
    }
}
