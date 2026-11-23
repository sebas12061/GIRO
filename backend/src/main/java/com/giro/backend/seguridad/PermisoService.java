package com.giro.backend.seguridad;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PermisoService {

    private final PermisoRepository repository;

    public PermisoService(PermisoRepository repository) {
        this.repository = repository;
    }

    public PermisoDto crear(CrearPermisoRequest request) {
        if (repository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un permiso con ese nombre");
        }
        Permiso permiso = repository.save(new Permiso(request.name()));
        return new PermisoDto(permiso.getId(), permiso.getName());
    }

    @Transactional(readOnly = true)
    public List<PermisoDto> listar() {
        return repository.findAll().stream()
            .map(permiso -> new PermisoDto(permiso.getId(), permiso.getName()))
            .toList();
    }

}
