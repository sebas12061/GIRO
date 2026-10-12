package com.giro.backend.pagos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PagoService {
    private final PagoRepository repository;
    private final PaymentStrategyFactory strategyFactory;
    public PagoService(PagoRepository repository, PaymentStrategyFactory strategyFactory) { this.repository = repository; this.strategyFactory = strategyFactory; }
    public PagoDto registrar(RegistrarPagoRequest request) {
        strategyFactory.forType(request.tipo()).validar(request.valor(), request.referencia());
        return toDto(repository.save(new Pago(request.restauranteId(), request.ventaId(), request.tipo(), request.valor(), request.referencia())));
    }
    @Transactional(readOnly = true)
    public List<PagoDto> listar(UUID restauranteId, UUID ventaId) { return repository.findAllByVentaIdAndRestauranteIdOrderByFechaHora(ventaId, restauranteId).stream().map(this::toDto).toList(); }
    public PagoDto verificar(UUID restauranteId, UUID pagoId) {
        Pago pago = repository.findById(pagoId).filter(value -> value.getRestauranteId().equals(restauranteId)).orElseThrow(() -> new IllegalArgumentException("Pago no encontrado para el restaurante"));
        pago.verificar();
        return toDto(repository.save(pago));
    }
    private PagoDto toDto(Pago pago) { return new PagoDto(pago.getId(), pago.getRestauranteId(), pago.getVentaId(), pago.getTipo(), pago.getValor(), pago.getReferencia(), pago.getStatus(), pago.getFechaHora()); }
}
