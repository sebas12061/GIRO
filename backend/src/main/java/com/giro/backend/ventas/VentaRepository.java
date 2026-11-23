package com.giro.backend.ventas;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface VentaRepository extends JpaRepository<Venta, UUID> {
    Optional<Venta> findByPedidoIdAndRestauranteId(UUID pedidoId, UUID restauranteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Venta> findLockedByPedidoIdAndRestauranteId(UUID pedidoId, UUID restauranteId);

    List<Venta> findAllByRestauranteIdAndFechaHoraBetweenOrderByFechaHoraDesc(
        UUID restauranteId, OffsetDateTime inicio, OffsetDateTime fin);
}
