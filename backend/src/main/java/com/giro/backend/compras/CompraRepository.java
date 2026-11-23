package com.giro.backend.compras;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, UUID> {
    Optional<Compra> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Compra> findAllByRestauranteIdOrderByFechaDesc(UUID restauranteId);

    Page<Compra> findAllByRestauranteId(UUID restauranteId, Pageable pageable);

    List<Compra> findAllByRestauranteIdAndFechaBetweenOrderByFechaDesc(
        UUID restauranteId, OffsetDateTime inicio, OffsetDateTime fin);
}
