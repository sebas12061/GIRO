package com.giro.backend.personal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TurnoRepository extends JpaRepository<Turno, UUID> {
    List<Turno> findAllByEmpleadoIdAndFechaOrderByHoraInicio(UUID empleadoId, LocalDate fecha);

    List<Turno> findAllByFechaBetween(LocalDate inicio, LocalDate fin);

    List<Turno> findAllByRestauranteIdAndFechaBetween(UUID restauranteId, LocalDate inicio, LocalDate fin);
}
