package com.giro.backend.caja;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, UUID> {
    List<MovimientoCaja> findAllByCajaIdAndRestauranteIdOrderByFechaHoraAsc(UUID cajaId, UUID restauranteId);

    List<MovimientoCaja> findAllByRestauranteIdAndOrigen(UUID restauranteId, String origen);
}
