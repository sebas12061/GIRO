package com.giro.backend.pedidos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class PedidoStateMachineTest {

    private final PedidoStateMachine stateMachine = new PedidoStateMachine();

    @Test
    void permiteTransicionValida() {
        assertDoesNotThrow(() -> stateMachine.validar(PedidoEstado.CREADO, PedidoEstado.CONFIRMADO));
    }

    @Test
    void noPermiteModificarPedidoCerrado() {
        assertThrows(IllegalStateException.class,
            () -> stateMachine.validar(PedidoEstado.CERRADO, PedidoEstado.CONFIRMADO));
    }
}
