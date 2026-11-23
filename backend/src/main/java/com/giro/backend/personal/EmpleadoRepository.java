package com.giro.backend.personal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, UUID> {
    Optional<Empleado> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Empleado> findAllByRestauranteIdAndStatusOrderByName(UUID restauranteId, EmpleadoStatus status);
}
