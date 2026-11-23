package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

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
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/cajas")
public class CajaController {
    private final CajaService service;
    public CajaController(CajaService service) { this.service = service; }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CajaDto abrir(@Valid @RequestBody AbrirCajaRequest request) { return service.abrir(request); }
    @GetMapping("/actual")
    public CajaDto actual(@RequestParam UUID restauranteId) { return service.actual(restauranteId); }
    @GetMapping("/efectivo-esperado")
    public BigDecimal efectivoEsperado(@RequestParam BigDecimal base, @RequestParam BigDecimal ingresos, @RequestParam BigDecimal gastos) { return service.efectivoEsperado(base, ingresos, gastos); }

    @PostMapping("/movimientos")
    @ResponseStatus(HttpStatus.CREATED)
    public MovimientoCajaDto registrarMovimiento(@Valid @RequestBody RegistrarMovimientoRequest request) {
        return service.registrarMovimiento(request);
    }

    @GetMapping("/movimientos")
    public List<MovimientoCajaDto> movimientos(@RequestParam UUID restauranteId, @RequestParam UUID cajaId) {
        return service.movimientos(restauranteId, cajaId);
    }

    @PostMapping("/cerrar")
    public CuadreDto cerrar(@Valid @RequestBody CerrarCajaRequest request) {
        return service.cerrar(request);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PutMapping("/cuadres/{cuadreId}/efectivo-real")
    public CuadreDto modificarCuadreCerrado(
        @PathVariable UUID cuadreId,
        @Valid @RequestBody ModificarCuadreRequest request
    ) {
        return service.modificarCuadreCerrado(cuadreId, request.efectivoReal(), request.usuarioAdministradorId());
    }
}
