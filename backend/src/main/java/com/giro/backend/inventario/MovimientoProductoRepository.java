package com.giro.backend.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoProductoRepository extends JpaRepository<MovimientoProducto, UUID> {
    List<MovimientoProducto> findAllByRestauranteIdAndProductoIdAndFechaOrderByFechaHoraAsc(
        UUID restauranteId, UUID productoId, LocalDate fecha);

    List<MovimientoProducto> findAllByRestauranteIdAndReferencia(UUID restauranteId, String referencia);

    List<MovimientoProducto> findAllByRestauranteIdAndProductoId(UUID restauranteId, UUID productoId);
}
