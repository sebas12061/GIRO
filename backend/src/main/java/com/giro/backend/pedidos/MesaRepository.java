package com.giro.backend.pedidos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MesaRepository extends JpaRepository<Mesa, UUID> {
    Optional<Mesa> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Mesa> findAllByRestauranteIdOrderByNumero(UUID restauranteId);
    boolean existsByRestauranteIdAndNumero(UUID restauranteId, Integer numero);
}
