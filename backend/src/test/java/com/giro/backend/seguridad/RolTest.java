package com.giro.backend.seguridad;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RolTest {

    @Test
    void rolPuedeAsignarPermisos() {
        Rol rol = new Rol("CAJA");
        Permiso permiso = new Permiso("PAGO_REGISTRAR");

        rol.getPermisos().add(permiso);

        assertTrue(rol.getPermisos().contains(permiso));
        assertFalse(rol.getPermisos().isEmpty());
    }
}
