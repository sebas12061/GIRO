package com.giro.backend.compras;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CompraTest {

    @Test
    void calculaTotalDesdeLosDetalles() {
        Compra compra = new Compra(UUID.randomUUID(), UUID.randomUUID(), null, "Compra de insumos");
        compra.agregarDetalle(new DetalleCompra(UUID.randomUUID(), new BigDecimal("2.000"), new BigDecimal("15000.00")));
        compra.agregarDetalle(new DetalleCompra(UUID.randomUUID(), new BigDecimal("3.000"), new BigDecimal("5000.00")));

        assertEquals(0, new BigDecimal("45000.00").compareTo(compra.getTotal()));
    }

    @Test
    void noPermiteAnularDosVeces() {
        Compra compra = new Compra(UUID.randomUUID(), UUID.randomUUID(), null, null);
        compra.anular();

        assertThrows(IllegalStateException.class, compra::anular);
    }
}
