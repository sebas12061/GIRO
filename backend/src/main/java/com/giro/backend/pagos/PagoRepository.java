package com.giro.backend.pagos;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, UUID> {
    List<Pago> findAllByVentaIdAndRestauranteIdOrderByFechaHora(UUID ventaId, UUID restauranteId);

    List<Pago> findAllByRestauranteIdAndTipoAndStatus(UUID restauranteId, TipoPago tipo, PagoStatus status);

    List<Pago> findAllByRestauranteIdAndFechaHoraBetweenOrderByFechaHoraDesc(
        UUID restauranteId, OffsetDateTime inicio, OffsetDateTime fin);
}
