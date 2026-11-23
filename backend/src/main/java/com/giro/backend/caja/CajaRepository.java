package com.giro.backend.caja;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface CajaRepository extends JpaRepository<Caja, UUID> {
    Optional<Caja> findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(UUID restauranteId, CajaStatus status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Caja> findLockedByIdAndRestauranteId(UUID id, UUID restauranteId);

    List<Caja> findAllByRestauranteId(UUID restauranteId);
}
