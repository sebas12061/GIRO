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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDto crear(@Valid @RequestBody CrearUsuarioRequest request) {
        return service.crear(request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping
    public List<UsuarioDto> listar(@RequestParam UUID restauranteId) {
        return service.listar(restauranteId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{usuarioId}/activar")
    public UsuarioDto activar(@PathVariable UUID usuarioId, @RequestParam UUID restauranteId) {
        return service.activar(restauranteId, usuarioId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{usuarioId}/desactivar")
    public UsuarioDto desactivar(@PathVariable UUID usuarioId, @RequestParam UUID restauranteId) {
        return service.desactivar(restauranteId, usuarioId);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{usuarioId}/password")
    public UsuarioDto cambiarPassword(@PathVariable UUID usuarioId, @RequestParam UUID restauranteId,
                                      @Valid @RequestBody CambiarPasswordRequest request) {
        return service.cambiarPassword(restauranteId, usuarioId, request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/{usuarioId}/roles")
    public UsuarioDto asignarRoles(@PathVariable UUID usuarioId, @RequestParam UUID restauranteId,
                                   @Valid @RequestBody AsignarRolesRequest request) {
        return service.asignarRoles(restauranteId, usuarioId, request);
    }
}
