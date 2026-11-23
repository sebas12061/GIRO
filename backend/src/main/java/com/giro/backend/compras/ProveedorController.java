package com.giro.backend.compras;

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
@RequestMapping("/api/v1/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorDto crear(@Valid @RequestBody CrearProveedorRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<ProveedorDto> listar(@RequestParam UUID restauranteId) {
        return service.listarActivos(restauranteId);
    }

    @PutMapping("/{proveedorId}/desactivar")
    public ProveedorDto desactivar(@PathVariable UUID proveedorId, @RequestParam UUID restauranteId) {
        return service.desactivar(restauranteId, proveedorId);
    }
}
