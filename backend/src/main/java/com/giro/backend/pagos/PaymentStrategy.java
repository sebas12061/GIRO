package com.giro.backend.pagos;

import java.math.BigDecimal;

public interface PaymentStrategy {

    TipoPago tipo();

    void validar(BigDecimal valor, String referencia);
}
