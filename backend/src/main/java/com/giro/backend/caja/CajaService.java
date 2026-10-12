package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.pagos.CashExpectedCalculator;

@Service
@Transactional
public class CajaService {
    private final CajaRepository repository;
    private final CashExpectedCalculator cashCalculator;
    public CajaService(CajaRepository repository, CashExpectedCalculator cashCalculator) { this.repository = repository; this.cashCalculator = cashCalculator; }
    public CajaDto abrir(AbrirCajaRequest request) {
        repository.findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(request.restauranteId(), CajaStatus.OPEN).ifPresent(c -> { throw new IllegalStateException("Ya existe una caja abierta"); });
        return toDto(repository.save(new Caja(request.restauranteId(), request.baseInicial())));
    }
    @Transactional(readOnly = true)
    public CajaDto actual(UUID restauranteId) { return repository.findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(restauranteId, CajaStatus.OPEN).map(this::toDto).orElseThrow(() -> new IllegalArgumentException("No hay caja abierta")); }
    public BigDecimal efectivoEsperado(BigDecimal base, BigDecimal ingresos, BigDecimal gastos) { return cashCalculator.calculate(base, ingresos, gastos); }
    private CajaDto toDto(Caja caja) { return new CajaDto(caja.getId(), caja.getRestauranteId(), caja.getBaseInicial(), caja.getFechaApertura(), caja.getStatus()); }
}
