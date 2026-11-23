package com.giro.backend.pedidos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    Optional<Pedido> findByIdAndRestauranteId(UUID id, UUID restauranteId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Pedido> findLockedByIdAndRestauranteId(UUID id, UUID restauranteId);

    List<Pedido> findAllByRestauranteIdOrderByFechaHoraDesc(UUID restauranteId);

    Page<Pedido> findAllByRestauranteId(UUID restauranteId, Pageable pageable);

    List<Pedido> findAllByRestauranteIdAndMesaId(UUID restauranteId, UUID mesaId);

    List<Pedido> findAllByRestauranteIdAndEstadoInOrderByFechaHoraAsc(
        UUID restauranteId,
        List<PedidoEstado> estados
    );
}
