package com.giro.backend.pagos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class TransferenciaPaymentStrategy implements PaymentStrategy {

    @Override
    public TipoPago tipo() {
        return TipoPago.TRANSFERENCIA;
    }

    @Override
    public void validar(BigDecimal valor, String referencia) {
        if (valor == null || valor.signum() <= 0 || referencia == null || referencia.isBlank()) {
            throw new IllegalArgumentException("La transferencia requiere valor y referencia");
        }
    }
}
