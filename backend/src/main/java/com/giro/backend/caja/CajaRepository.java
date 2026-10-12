package com.giro.backend.caja;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CajaRepository extends JpaRepository<Caja, UUID> {
    Optional<Caja> findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(UUID restauranteId, CajaStatus status);
}
