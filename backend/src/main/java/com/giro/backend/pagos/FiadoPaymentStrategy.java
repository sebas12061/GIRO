package com.giro.backend.pagos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class FiadoPaymentStrategy implements PaymentStrategy {

    @Override
    public TipoPago tipo() {
        return TipoPago.FIADO;
    }

    @Override
    public void validar(BigDecimal valor, String referencia) {
        if (valor == null || valor.signum() <= 0 || referencia == null || referencia.isBlank()) {
            throw new IllegalArgumentException("El fiado requiere valor y cliente identificado");
        }
    }
}
