package com.giro.backend.pagos;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class PaymentStrategyFactory {

    private final Map<TipoPago, PaymentStrategy> strategies = new EnumMap<>(TipoPago.class);

    public PaymentStrategyFactory(List<PaymentStrategy> strategies) {
        strategies.forEach(strategy -> this.strategies.put(strategy.tipo(), strategy));
    }

    public PaymentStrategy forType(TipoPago tipo) {
        PaymentStrategy strategy = strategies.get(tipo);
        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de pago no soportado: " + tipo);
        }
        return strategy;
    }
}
