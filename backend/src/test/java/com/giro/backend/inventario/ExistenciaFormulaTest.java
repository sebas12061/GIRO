package com.giro.backend.inventario;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class ExistenciaFormulaTest {

    @Test
    void calculaExistenciaDelEjemploOperativo() {
        BigDecimal amanece = new BigDecimal("10");
        BigDecimal llega = new BigDecimal("5");
        BigDecimal almuerzo = new BigDecimal("8");
        BigDecimal personal = new BigDecimal("1");

        BigDecimal existencia = amanece.add(llega).subtract(almuerzo).subtract(personal);

        assertEquals(0, new BigDecimal("6").compareTo(existencia));
    }

    @Test
    void ajustaExistenciaPositiva() {
        BigDecimal base = new BigDecimal("6");
        BigDecimal ajuste = new BigDecimal("2");

        assertEquals(0, new BigDecimal("8").compareTo(base.add(ajuste)));
    }
}
