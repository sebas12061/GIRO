package com.giro.backend.clientes;

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
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteDto crear(@Valid @RequestBody CrearClienteRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<ClienteDto> listar(@RequestParam UUID restauranteId) {
        return service.listarActivos(restauranteId);
    }

    @PutMapping("/{clienteId}/desactivar")
    public ClienteDto desactivar(@PathVariable UUID clienteId, @RequestParam UUID restauranteId) {
        return service.desactivar(restauranteId, clienteId);
    }
}
