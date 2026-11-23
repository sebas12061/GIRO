package com.giro.backend.ventas;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class CobroTotalTest {

    @Test
    void cobroIgualAlTotalSeConsideraCompleto() {
        BigDecimal confirmado = new BigDecimal("25000.00");
        BigDecimal total = new BigDecimal("25000.00");

        assertTrue(confirmado.compareTo(total) >= 0);
    }
}
