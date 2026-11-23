package com.giro.backend.inventario;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class StockNegativoTest {

    @Test
    void consumoMayorQueExistenciaDejaStockNegativo() {
        BigDecimal existencia = new BigDecimal("5.000");
        BigDecimal consumo = new BigDecimal("6.000");

        assertTrue(existencia.subtract(consumo).signum() < 0);
    }
}
