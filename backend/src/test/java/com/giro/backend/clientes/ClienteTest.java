package com.giro.backend.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class ClienteTest {

    @Test
    void clienteFiadoPuedeDesactivarse() {
        Cliente cliente = new Cliente(UUID.randomUUID(), "Cliente prueba", "3000000000",
            "Referencia", TipoCliente.FIADO);

        cliente.desactivar();

        assertEquals(ClienteStatus.INACTIVE, cliente.getStatus());
    }
}
