package com.giro.backend.inventario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inventario")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @PostMapping("/movimientos")
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoProductoDto registrarMovimiento(
        @Valid @RequestBody RegistrarMovimientoProductoRequest request
    ) {
        return service.registrarMovimiento(request);
    }

    @GetMapping("/movimientos")
    public List<MovimientoProductoDto> movimientos(
        @RequestParam UUID restauranteId,
        @RequestParam UUID productoId,
        @RequestParam LocalDate fecha
    ) {
        return service.movimientos(restauranteId, productoId, fecha);
    }

    @GetMapping("/existencia")
    public ExistenciaDto existencia(
        @RequestParam UUID restauranteId,
        @RequestParam UUID productoId,
        @RequestParam LocalDate fecha
    ) {
        return service.calcularExistencia(restauranteId, productoId, fecha);
    }

    @PostMapping("/cierres")
    @ResponseStatus(HttpStatus.CREATED)
    public CierreOperativoDto cerrar(@Valid @RequestBody CerrarInventarioRequest request) {
        return service.cerrar(request);
    }
}
