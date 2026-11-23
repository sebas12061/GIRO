package com.giro.backend.personal;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/v1/empleados")
public class EmpleadoController {

    private final EmpleadoService service;

    public EmpleadoController(EmpleadoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoDto crear(@Valid @RequestBody CrearEmpleadoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<EmpleadoDto> listar(@RequestParam UUID restauranteId) {
        return service.listarActivos(restauranteId);
    }

    @PutMapping("/{empleadoId}/desactivar")
    public EmpleadoDto desactivar(@PathVariable UUID empleadoId, @RequestParam UUID restauranteId) {
        return service.desactivar(restauranteId, empleadoId);
    }
}
