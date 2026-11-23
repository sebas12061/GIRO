package com.giro.backend.ventas;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import com.giro.backend.pedidos.PedidoEstado;
import com.giro.backend.pedidos.PedidoStateMachine;

class FlujoPedidoVentaTest {

    @Test
    void cobroCompletoPasaPedidoPagadoYCerrado() {
        PedidoStateMachine stateMachine = new PedidoStateMachine();

        assertDoesNotThrow(() -> {
            stateMachine.validar(PedidoEstado.PENDIENTE_PAGO, PedidoEstado.PAGADO);
            stateMachine.validar(PedidoEstado.PAGADO, PedidoEstado.CERRADO);
        });
    }
}
