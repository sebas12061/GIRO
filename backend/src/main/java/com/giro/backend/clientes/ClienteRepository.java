package com.giro.backend.clientes;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Cliente> findAllByRestauranteIdAndStatusOrderByName(UUID restauranteId, ClienteStatus status);
}
