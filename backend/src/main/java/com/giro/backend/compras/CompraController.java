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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/v1/compras")
public class CompraController {

    private final CompraService service;

    public CompraController(CompraService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompraDto registrar(@Valid @RequestBody CrearCompraRequest request) {
        return service.registrar(request);
    }

    @GetMapping
    public Page<CompraDto> listar(@RequestParam UUID restauranteId,
                                  @PageableDefault(size = 20, sort = "fecha") Pageable pageable) {
        return service.listar(restauranteId, pageable);
    }

    @PutMapping("/{compraId}/anular")
    public CompraDto anular(@PathVariable UUID compraId, @RequestParam UUID restauranteId) {
        return service.anular(restauranteId, compraId);
    }
}
