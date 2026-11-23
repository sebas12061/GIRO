package com.giro.backend.seguridad;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/administracion")
public class RolController {

    private final RolService rolService;
    private final PermisoService permisoService;

    public RolController(RolService rolService, PermisoService permisoService) {
        this.rolService = rolService;
        this.permisoService = permisoService;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/roles")
    @ResponseStatus(HttpStatus.CREATED)
    public RolDto crearRol(@Valid @RequestBody CrearRolRequest request) {
        return rolService.crear(request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/roles")
    public List<RolDto> listarRoles() {
        return rolService.listar();
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/roles/{rolId}/permisos")
    public RolDto asignarPermisos(@PathVariable UUID rolId,
                                  @Valid @RequestBody AsignarPermisosRequest request) {
        return rolService.asignarPermisos(rolId, request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/permisos")
    @ResponseStatus(HttpStatus.CREATED)
    public PermisoDto crearPermiso(@Valid @RequestBody CrearPermisoRequest request) {
        return permisoService.crear(request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/permisos")
    public List<PermisoDto> listarPermisos() {
        return permisoService.listar();
    }
}
