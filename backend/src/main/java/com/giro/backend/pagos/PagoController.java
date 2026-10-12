package com.giro.backend.pagos;

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
@RequestMapping("/api/v1/pagos")
public class PagoController {
    private final PagoService service;
    public PagoController(PagoService service) { this.service = service; }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PagoDto registrar(@Valid @RequestBody RegistrarPagoRequest request) { return service.registrar(request); }
    @GetMapping
    public List<PagoDto> listar(@RequestParam UUID restauranteId, @RequestParam UUID ventaId) { return service.listar(restauranteId, ventaId); }
    @PutMapping("/{pagoId}/verificar")
    public PagoDto verificar(@PathVariable UUID pagoId, @RequestParam UUID restauranteId) { return service.verificar(restauranteId, pagoId); }
}
