package com.giro.backend.pagos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PaymentStrategyTest {

    @Test
    void transferenciaExigeReferencia() {
        PaymentStrategy strategy = new TransferenciaPaymentStrategy();

        assertThrows(IllegalArgumentException.class,
            () -> strategy.validar(BigDecimal.TEN, null));
    }

    @Test
    void efectivoValidoSeAcepta() {
        PaymentStrategy strategy = new EfectivoPaymentStrategy();

        assertDoesNotThrow(() -> strategy.validar(BigDecimal.TEN, null));
    }
}
