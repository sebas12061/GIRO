package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
