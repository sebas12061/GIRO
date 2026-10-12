package com.giro.backend.caja;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.giro.backend.pagos.DefaultCashExpectedCalculator;

class CajaServiceTest {
    @Test
    void calculaEfectivoEsperado() {
        CajaService service = new CajaService(null, new DefaultCashExpectedCalculator());
        assertEquals(new BigDecimal("125.00"), service.efectivoEsperado(new BigDecimal("100.00"), new BigDecimal("50.00"), new BigDecimal("25.00")));
    }
}
