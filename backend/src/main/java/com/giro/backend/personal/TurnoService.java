package com.giro.backend.personal;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TurnoService {

    private final TurnoRepository turnoRepository;
    private final EmpleadoRepository empleadoRepository;

    public TurnoService(TurnoRepository turnoRepository, EmpleadoRepository empleadoRepository) {
        this.turnoRepository = turnoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    public TurnoDto crear(CrearTurnoRequest request) {
        Empleado empleado = empleadoRepository.findByIdAndRestauranteId(request.empleadoId(), request.restauranteId())
            .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado para el restaurante"));
        if (empleado.getStatus() != EmpleadoStatus.ACTIVE) {
            throw new IllegalStateException("No se pueden asignar turnos a empleados inactivos");
        }
        if (!request.horaInicio().isBefore(request.horaFin())) {
            throw new IllegalArgumentException("La hora de inicio debe ser anterior a la hora fin");
        }

        boolean solapa = turnoRepository.findAllByEmpleadoIdAndFechaOrderByHoraInicio(request.empleadoId(), request.fecha())
            .stream()
            .filter(turno -> turno.getStatus() != TurnoStatus.CANCELADO)
            .anyMatch(turno -> request.horaInicio().isBefore(turno.getHoraFin())
                && turno.getHoraInicio().isBefore(request.horaFin()));
        if (solapa) {
            throw new IllegalStateException("El turno se solapa con otro turno del empleado");
        }

        Turno turno = turnoRepository.save(new Turno(request.restauranteId(), request.empleadoId(), request.fecha(), request.tipo(),
            request.horaInicio(), request.horaFin(), request.valor()));
        return toDto(turno);
    }

    @Transactional(readOnly = true)
    public List<TurnoDto> listar(UUID empleadoId, LocalDate fecha) {
        return turnoRepository.findAllByEmpleadoIdAndFechaOrderByHoraInicio(empleadoId, fecha)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public TurnoDto iniciar(UUID turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));
        turno.iniciar();
        return toDto(turnoRepository.save(turno));
    }

    public TurnoDto finalizar(UUID turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));
        turno.finalizar();
        return toDto(turnoRepository.save(turno));
    }

    public TurnoDto cancelar(UUID turnoId) {
        Turno turno = turnoRepository.findById(turnoId)
            .orElseThrow(() -> new IllegalArgumentException("Turno no encontrado"));
        turno.cancelar();
        return toDto(turnoRepository.save(turno));
    }

    private TurnoDto toDto(Turno turno) {
        return new TurnoDto(turno.getId(), turno.getEmpleadoId(), turno.getFecha(), turno.getTipo(),
            turno.getHoraInicio(), turno.getHoraFin(), turno.getValor(), turno.getStatus());
    }
}
