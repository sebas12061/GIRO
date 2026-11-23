package com.giro.backend.cocina;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.giro.backend.pedidos.PedidoEstado;
import com.giro.backend.pedidos.PedidoStateMachine;

class CocinaStateTest {

    private final PedidoStateMachine stateMachine = new PedidoStateMachine();

    @Test
    void cocinaPuedeConfirmarPedidoCreado() {
        assertDoesNotThrow(() ->
            stateMachine.validar(PedidoEstado.CREADO, PedidoEstado.CONFIRMADO));
    }

    @Test
    void cocinaPuedeIniciarConfirmado() {
        assertDoesNotThrow(() ->
            stateMachine.validar(PedidoEstado.CONFIRMADO, PedidoEstado.EN_PREPARACION));
    }

    @Test
    void cocinaPuedeMarcarPreparado() {
        assertDoesNotThrow(() ->
            stateMachine.validar(PedidoEstado.EN_PREPARACION, PedidoEstado.PREPARADO));
    }

    @Test
    void cocinaNoPuedePrepararPedidoCancelado() {
        assertThrows(IllegalStateException.class, () ->
            stateMachine.validar(PedidoEstado.CANCELADO, PedidoEstado.PREPARADO));
    }
}
