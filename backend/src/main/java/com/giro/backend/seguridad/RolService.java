package com.giro.backend.seguridad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RolService {

    private final RolRepository rolRepository;
    private final PermisoRepository permisoRepository;

    public RolService(RolRepository rolRepository, PermisoRepository permisoRepository) {
        this.rolRepository = rolRepository;
        this.permisoRepository = permisoRepository;
    }

    @SeguridadAudit(action = "CREAR_ROL")
    public RolDto crear(CrearRolRequest request) {
        if (rolRepository.findByName(request.name()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un rol con ese nombre");
        }
        Set<Permiso> permisos = cargarPermisos(request.permisoIds());
        Rol rol = new Rol(request.name());
        rol.getPermisos().clear();
        rol.getPermisos().addAll(permisos);
        return toDto(rolRepository.save(rol));
    }

    @Transactional(readOnly = true)
    public List<RolDto> listar() {
        return rolRepository.findAll().stream().map(this::toDto).toList();
    }

    @SeguridadAudit(action = "ASIGNAR_PERMISOS_ROL")
    public RolDto asignarPermisos(UUID rolId, AsignarPermisosRequest request) {
        Rol rol = rolRepository.findById(rolId)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));
        Set<Permiso> permisos = cargarPermisos(request.permisoIds());
        rol.getPermisos().clear();
        rol.getPermisos().addAll(permisos);
        return toDto(rolRepository.save(rol));
    }

    private Set<Permiso> cargarPermisos(Set<UUID> permisoIds) {
        Set<Permiso> permisos = new HashSet<>();
        if (permisoIds == null) {
            return permisos;
        }
        permisoIds.forEach(permisoId -> {
            Permiso permiso = permisoRepository.findById(permisoId)
                .orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado: " + permisoId));
            permisos.add(permiso);
        });
        return permisos;
    }

    private RolDto toDto(Rol rol) {
        Set<String> permisos = rol.getPermisos().stream()
            .map(Permiso::getName)
            .collect(Collectors.toSet());
        return new RolDto(rol.getId(), rol.getName(), permisos);
    }
}
