package com.giro.backend.inventario;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CierreOperativoRepository extends JpaRepository<CierreOperativo, UUID> {
    Optional<CierreOperativo> findByRestauranteIdAndFecha(UUID restauranteId, LocalDate fecha);
}
