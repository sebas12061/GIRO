package com.giro.backend.personal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class EmpleadoTest {

    @Test
    void empleadoPuedeDesactivarse() {
        Empleado empleado = new Empleado(UUID.randomUUID(), "Ana Perez", "123", "3000000000");

        empleado.desactivar();

        assertEquals(EmpleadoStatus.INACTIVE, empleado.getStatus());
    }
}
