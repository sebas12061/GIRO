package com.giro.backend.pedidos;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, UUID> {

    Optional<Pedido> findByIdAndRestauranteId(UUID id, UUID restauranteId);

    List<Pedido> findAllByRestauranteIdOrderByFechaHoraDesc(UUID restauranteId);
}
