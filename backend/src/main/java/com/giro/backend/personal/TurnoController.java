package com.giro.backend.personal;

import java.time.LocalDate;
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
@RequestMapping("/api/v1/turnos")
public class TurnoController {

    private final TurnoService service;

    public TurnoController(TurnoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TurnoDto crear(@Valid @RequestBody CrearTurnoRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<TurnoDto> listar(@RequestParam UUID empleadoId, @RequestParam LocalDate fecha) {
        return service.listar(empleadoId, fecha);
    }

    @PutMapping("/{turnoId}/iniciar")
    public TurnoDto iniciar(@PathVariable UUID turnoId) {
        return service.iniciar(turnoId);
    }

    @PutMapping("/{turnoId}/finalizar")
    public TurnoDto finalizar(@PathVariable UUID turnoId) {
        return service.finalizar(turnoId);
    }

    @PutMapping("/{turnoId}/cancelar")
    public TurnoDto cancelar(@PathVariable UUID turnoId) {
        return service.cancelar(turnoId);
    }
}
