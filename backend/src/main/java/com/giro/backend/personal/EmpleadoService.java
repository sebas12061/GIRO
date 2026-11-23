package com.giro.backend.personal;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository repository;

    public EmpleadoService(EmpleadoRepository repository) {
        this.repository = repository;
    }

    public EmpleadoDto crear(CrearEmpleadoRequest request) {
        Empleado empleado = repository.save(new Empleado(
            request.restauranteId(), request.name(), request.documento(), request.telefono()));
        return toDto(empleado);
    }

    @Transactional(readOnly = true)
    public List<EmpleadoDto> listarActivos(UUID restauranteId) {
        return repository.findAllByRestauranteIdAndStatusOrderByName(restauranteId, EmpleadoStatus.ACTIVE)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public EmpleadoDto desactivar(UUID restauranteId, UUID empleadoId) {
        Empleado empleado = repository.findByIdAndRestauranteId(empleadoId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado para el restaurante"));
        empleado.desactivar();
        return toDto(repository.save(empleado));
    }

    private EmpleadoDto toDto(Empleado empleado) {
        return new EmpleadoDto(empleado.getId(), empleado.getRestauranteId(), empleado.getName(),
            empleado.getDocumento(), empleado.getTelefono(), empleado.getStatus());
    }
}
