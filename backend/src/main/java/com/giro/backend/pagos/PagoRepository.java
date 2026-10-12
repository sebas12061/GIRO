package com.giro.backend.pagos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
    List<Pago> findAllByVentaIdAndRestauranteIdOrderByFechaHora(UUID ventaId, UUID restauranteId);
}
