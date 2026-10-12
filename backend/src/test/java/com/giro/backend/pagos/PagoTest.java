package com.giro.backend.pagos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class PagoTest {
    @Test
    void transferenciaIniciaPendienteDeVerificacion() {
        Pago pago = new Pago(UUID.randomUUID(), UUID.randomUUID(), TipoPago.TRANSFERENCIA, BigDecimal.TEN, "TRX-1");
        assertEquals(PagoStatus.PENDING_VERIFICATION, pago.getStatus());
        pago.verificar();
        assertEquals(PagoStatus.CONFIRMED, pago.getStatus());
    }
}
