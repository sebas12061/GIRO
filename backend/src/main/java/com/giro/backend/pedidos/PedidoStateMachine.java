package com.giro.backend.pedidos;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class PedidoStateMachine {

    private final Map<PedidoEstado, Set<PedidoEstado>> transiciones = new EnumMap<>(PedidoEstado.class);

    public PedidoStateMachine() {
        transiciones.put(PedidoEstado.CREADO, EnumSet.of(PedidoEstado.CONFIRMADO, PedidoEstado.CANCELADO));
        transiciones.put(PedidoEstado.CONFIRMADO, EnumSet.of(PedidoEstado.EN_PREPARACION, PedidoEstado.CANCELADO));
        transiciones.put(PedidoEstado.EN_PREPARACION, EnumSet.of(PedidoEstado.PREPARADO, PedidoEstado.CANCELADO));
        transiciones.put(PedidoEstado.PREPARADO, EnumSet.of(PedidoEstado.SERVIDO));
        transiciones.put(PedidoEstado.SERVIDO, EnumSet.of(PedidoEstado.PENDIENTE_PAGO));
        transiciones.put(PedidoEstado.PENDIENTE_PAGO, EnumSet.of(PedidoEstado.PAGADO));
        transiciones.put(PedidoEstado.PAGADO, EnumSet.of(PedidoEstado.CERRADO));
        transiciones.put(PedidoEstado.CERRADO, EnumSet.noneOf(PedidoEstado.class));
        transiciones.put(PedidoEstado.CANCELADO, EnumSet.noneOf(PedidoEstado.class));
    }

    public void validar(PedidoEstado actual, PedidoEstado siguiente) {
        if (!transiciones.getOrDefault(actual, Set.of()).contains(siguiente)) {
            throw new IllegalStateException("Transicion no permitida: " + actual + " -> " + siguiente);
        }
    }
}
