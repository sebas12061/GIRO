package com.giro.backend.pedidos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class MesaTest {

    @Test
    void mesaPuedeOcuparseYLiberarse() {
        Mesa mesa = new Mesa(UUID.randomUUID(), 1, 4);
        mesa.ocupar();
        assertEquals(MesaEstado.OCUPADA, mesa.getEstado());
        mesa.liberar();
        assertEquals(MesaEstado.DISPONIBLE, mesa.getEstado());
    }
}
