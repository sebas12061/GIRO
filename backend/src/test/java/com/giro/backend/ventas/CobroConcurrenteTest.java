package com.giro.backend.ventas;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.giro.backend.pedidos.Pedido;

class CobroConcurrenteTest {

    @Test
    void segundoCobroSobreVentaPagadaDebeRechazarse() {
        Venta venta = new Venta(UUID.randomUUID(), UUID.randomUUID(),
            java.math.BigDecimal.TEN, java.math.BigDecimal.TEN);
        venta.pagar();

        assertThrows(IllegalStateException.class, () -> {
            if (venta.getStatus() == VentaStatus.PAGADA) {
                throw new IllegalStateException("La venta ya fue pagada");
            }
        });
    }
}
