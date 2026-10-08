package com.giro.backend.pedidos;

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
@RequestMapping("/api/v1/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDto crear(@Valid @RequestBody CrearPedidoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<PedidoDto> listar(@RequestParam UUID restauranteId) {
        return service.listar(restauranteId);
    }

    @PutMapping("/{pedidoId}/estado")
    public PedidoDto cambiarEstado(
        @PathVariable UUID pedidoId,
        @RequestParam UUID restauranteId,
        @RequestParam PedidoEstado estado
    ) {
        return service.cambiarEstado(restauranteId, pedidoId, estado);
    }
}
