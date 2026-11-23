package com.giro.backend.caja;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class CuadreTest {

    @Test
    void calculaDiferenciaComoSobranterSiElRealEsMayor() {
        Cuadre cuadre = new Cuadre(UUID.randomUUID(), new BigDecimal("100.00"),
            new BigDecimal("50.00"), BigDecimal.ZERO, BigDecimal.ZERO,
            new BigDecimal("10.00"), new BigDecimal("140.00"),
            new BigDecimal("150.00"), UUID.randomUUID());

        assertEquals(new BigDecimal("10.00"), cuadre.getDiferencia());
    }

    @Test
    void modificaCuadreCerradoYRecalculaDiferencia() {
        Cuadre cuadre = new Cuadre(UUID.randomUUID(), new BigDecimal("100.00"),
            new BigDecimal("50.00"), BigDecimal.ZERO, BigDecimal.ZERO,
            new BigDecimal("10.00"), new BigDecimal("140.00"),
            new BigDecimal("150.00"), UUID.randomUUID());
        cuadre.cerrar();

        cuadre.modificarEfectivoReal(new BigDecimal("130.00"));

        assertEquals(new BigDecimal("-10.00"), cuadre.getDiferencia());
    }
}
