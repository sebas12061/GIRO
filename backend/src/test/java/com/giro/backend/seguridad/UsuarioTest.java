package com.giro.backend.seguridad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UsuarioTest {

    @Test
    void passwordSeHasheaConBCrypt() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Usuario usuario = new Usuario(UUID.randomUUID(), "operador", encoder.encode("clave-segura"));

        assertTrue(encoder.matches("clave-segura", usuario.getPassword()));
        assertFalse(encoder.matches("clave-incorrecta", usuario.getPassword()));
    }

    @Test
    void usuarioPuedeActivarseYDesactivarse() {
        Usuario usuario = new Usuario(UUID.randomUUID(), "operador", "hash");

        usuario.desactivar();
        assertFalse(usuario.isActivo());

        usuario.activar();
        assertTrue(usuario.isActivo());
    }

    @Test
    void usernameYRestauranteSeConservan() {
        UUID restauranteId = UUID.randomUUID();
        Usuario usuario = new Usuario(restauranteId, "operador", "hash");

        assertEquals(restauranteId, usuario.getRestauranteId());
        assertEquals("operador", usuario.getUsername());
    }
}
