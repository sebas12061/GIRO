package com.giro.backend.pagos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class EfectivoPaymentStrategy implements PaymentStrategy {

    @Override
    public TipoPago tipo() {
        return TipoPago.EFECTIVO;
    }

    @Override
    public void validar(BigDecimal valor, String referencia) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("El pago en efectivo debe ser mayor que cero");
        }
    }
}
