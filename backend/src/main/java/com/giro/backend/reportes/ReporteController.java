package com.giro.backend.reportes;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @GetMapping("/operativo")
    public ReporteOperativoDto operativo(@RequestParam UUID restauranteId,
                                         @RequestParam LocalDate desde,
                                         @RequestParam LocalDate hasta) {
        return service.operativo(new ReportePeriodoRequest(restauranteId, desde, hasta));
    }
}
