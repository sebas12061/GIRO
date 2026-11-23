package com.giro.backend.cocina;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.giro.backend.pedidos.PedidoDto;

@RestController
@RequestMapping("/api/v1/cocina")
public class CocinaController {

    private final CocinaService service;

    public CocinaController(CocinaService service) {
        this.service = service;
    }

    @GetMapping("/pendientes")
    public List<PedidoDto> pendientes(@RequestParam UUID restauranteId) {
        return service.listarPendientes(restauranteId);
    }

    @PutMapping("/pedidos/{pedidoId}/confirmar")
    public PedidoDto confirmar(@PathVariable UUID pedidoId, @RequestParam UUID restauranteId) {
        return service.confirmarPedido(restauranteId, pedidoId);
    }

    @PutMapping("/pedidos/{pedidoId}/iniciar")
    public PedidoDto iniciar(@PathVariable UUID pedidoId, @RequestParam UUID restauranteId) {
        return service.iniciarPreparacion(restauranteId, pedidoId);
    }

    @PutMapping("/pedidos/{pedidoId}/preparado")
    public PedidoDto preparado(@PathVariable UUID pedidoId, @RequestParam UUID restauranteId) {
        return service.marcarPreparado(restauranteId, pedidoId);
    }
}
