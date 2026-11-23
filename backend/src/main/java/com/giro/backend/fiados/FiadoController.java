package com.giro.backend.fiados;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fiados")
public class FiadoController {

    private final FiadoService service;

    public FiadoController(FiadoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FiadoDto crear(@Valid @RequestBody CrearFiadoRequest request) {
        return service.crear(request);
    }

    @PostMapping("/movimientos")
    @ResponseStatus(HttpStatus.CREATED)
    public FiadoDto registrarMovimiento(@Valid @RequestBody RegistrarMovimientoFiadoRequest request) {
        return service.registrarMovimiento(request);
    }

    @GetMapping
    public List<FiadoDto> listar(@RequestParam UUID restauranteId) {
        return service.listar(restauranteId);
    }

    @GetMapping("/{fiadoId}")
    public FiadoDto consultar(@PathVariable UUID fiadoId, @RequestParam UUID restauranteId) {
        return service.consultar(restauranteId, fiadoId);
    }
}
