package com.giro.backend.fiados;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FiadoRepository extends JpaRepository<Fiado, UUID> {
    Optional<Fiado> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    Optional<Fiado> findByClienteIdAndRestauranteIdAndStatus(UUID clienteId, UUID restauranteId, FiadoStatus status);
    List<Fiado> findAllByRestauranteIdOrderByFechaInicioDesc(UUID restauranteId);
}
