package com.giro.backend.caja;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CuadreRepository extends JpaRepository<Cuadre, UUID> {
    Optional<Cuadre> findByCajaId(UUID cajaId);

    List<Cuadre> findAllByCajaId(UUID cajaId);
}
