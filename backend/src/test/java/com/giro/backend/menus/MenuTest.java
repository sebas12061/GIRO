package com.giro.backend.menus;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void opcionConservaPrecioOrdenado() {
        OpcionMenu opcion = new OpcionMenu(UUID.randomUUID(), null, "Almuerzo",
            new BigDecimal("18000.00"), 1);

        assertEquals(0, new BigDecimal("18000.00").compareTo(opcion.getPrice()));
        assertEquals(Integer.valueOf(1), opcion.getOrden());
    }
}
