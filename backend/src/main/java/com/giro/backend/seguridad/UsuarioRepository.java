package com.giro.backend.seguridad;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByUsername(String username);
    Optional<Usuario> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Usuario> findAllByRestauranteIdOrderByUsername(UUID restauranteId);
}
