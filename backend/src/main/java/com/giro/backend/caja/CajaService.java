package com.giro.backend.caja;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.pagos.CashExpectedCalculator;
import com.giro.backend.pagos.Pago;
import com.giro.backend.pagos.PagoRepository;
import com.giro.backend.pagos.PagoStatus;
import com.giro.backend.pagos.TipoPago;

@Service
@Transactional
public class CajaService {
    private final CajaRepository repository;
    private final MovimientoCajaRepository movimientoRepository;
    private final CuadreRepository cuadreRepository;
    private final PagoRepository pagoRepository;
    private final CashExpectedCalculator cashCalculator;

    public CajaService(CajaRepository repository, MovimientoCajaRepository movimientoRepository,
                       CuadreRepository cuadreRepository, PagoRepository pagoRepository,
                       CashExpectedCalculator cashCalculator) {
        this.repository = repository;
        this.movimientoRepository = movimientoRepository;
        this.cuadreRepository = cuadreRepository;
        this.pagoRepository = pagoRepository;
        this.cashCalculator = cashCalculator;
    }
    public CajaDto abrir(AbrirCajaRequest request) {
        repository.findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(request.restauranteId(), CajaStatus.OPEN).ifPresent(c -> { throw new IllegalStateException("Ya existe una caja abierta"); });
        return toDto(repository.save(new Caja(request.restauranteId(), request.baseInicial())));
    }
    @Transactional(readOnly = true)
    public CajaDto actual(UUID restauranteId) { return repository.findFirstByRestauranteIdAndStatusOrderByFechaAperturaDesc(restauranteId, CajaStatus.OPEN).map(this::toDto).orElseThrow(() -> new IllegalArgumentException("No hay caja abierta")); }
    public BigDecimal efectivoEsperado(BigDecimal base, BigDecimal ingresos, BigDecimal gastos) { return cashCalculator.calculate(base, ingresos, gastos); }

    public MovimientoCajaDto registrarMovimiento(RegistrarMovimientoRequest request) {
        Caja caja = repository.findLockedByIdAndRestauranteId(request.cajaId(), request.restauranteId())
            .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
            .orElseThrow(() -> new IllegalArgumentException("Caja no encontrada para el restaurante"));
        if (caja.getStatus() != CajaStatus.OPEN) {
            throw new IllegalStateException("No se pueden registrar movimientos en una caja cerrada");
        }

        MovimientoCaja movimiento = movimientoRepository.save(new MovimientoCaja(
            caja.getId(), request.restauranteId(), request.tipo(), request.concepto(),
            request.valor(), request.origen(), request.usuarioId()));
        return toMovimientoDto(movimiento);
    }

    @Transactional(readOnly = true)
    public List<MovimientoCajaDto> movimientos(UUID restauranteId, UUID cajaId) {
        repository.findById(cajaId)
            .filter(caja -> caja.getRestauranteId().equals(restauranteId))
            .orElseThrow(() -> new IllegalArgumentException("Caja no encontrada para el restaurante"));
        return movimientoRepository.findAllByCajaIdAndRestauranteIdOrderByFechaHoraAsc(cajaId, restauranteId)
            .stream()
            .map(this::toMovimientoDto)
            .toList();
    }

    public CuadreDto cerrar(CerrarCajaRequest request) {
        Caja caja = repository.findLockedByIdAndRestauranteId(request.cajaId(), request.restauranteId())
            .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
            .orElseThrow(() -> new IllegalArgumentException("Caja no encontrada para el restaurante"));
        if (caja.getStatus() != CajaStatus.OPEN) {
            throw new IllegalStateException("La caja ya esta cerrada");
        }

        cuadreRepository.findByCajaId(caja.getId()).ifPresent(cuadre -> {
            throw new IllegalStateException("La caja ya tiene un cuadre");
        });

        BigDecimal gastos = movimientoRepository
            .findAllByCajaIdAndRestauranteIdOrderByFechaHoraAsc(caja.getId(), request.restauranteId())
            .stream()
            .filter(movimiento -> movimiento.getTipo() == TipoMovimientoCaja.GASTO)
            .map(MovimientoCaja::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal efectivo = sumarPagos(request.restauranteId(), TipoPago.EFECTIVO, PagoStatus.CONFIRMED);
        BigDecimal transferencias = sumarPagos(request.restauranteId(), TipoPago.TRANSFERENCIA, PagoStatus.CONFIRMED);
        BigDecimal fiados = sumarPagos(request.restauranteId(), TipoPago.FIADO, PagoStatus.CONFIRMED);
        BigDecimal efectivoEsperado = cashCalculator.calculate(caja.getBaseInicial(), efectivo, gastos);

        Cuadre cuadre = cuadreRepository.save(new Cuadre(caja.getId(), caja.getBaseInicial(), efectivo,
            transferencias, fiados, gastos, efectivoEsperado, request.efectivoReal(), request.usuarioId()));
        cuadre.cerrar();
        cuadreRepository.save(cuadre);
        caja.cerrar();
        repository.save(caja);
        return toCuadreDto(cuadre);
    }

    @CajaAudit(action = "MODIFICAR_CUADRE_CERRADO")
    public CuadreDto modificarCuadreCerrado(UUID cuadreId, BigDecimal nuevoEfectivoReal,
                                             UUID usuarioAdministradorId) {
        Cuadre cuadre = cuadreRepository.findById(cuadreId)
            .orElseThrow(() -> new IllegalArgumentException("Cuadre no encontrado"));
        if (cuadre.getStatus() != CuadreStatus.CLOSED) {
            throw new IllegalStateException("Solo se pueden modificar cuadres cerrados");
        }

        cuadre.modificarEfectivoReal(nuevoEfectivoReal);
        cuadreRepository.save(cuadre);
        return toCuadreDto(cuadre);
    }

    private BigDecimal sumarPagos(UUID restauranteId, TipoPago tipo, PagoStatus status) {
        return pagoRepository.findAllByRestauranteIdAndTipoAndStatus(restauranteId, tipo, status)
            .stream()
            .map(Pago::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MovimientoCajaDto toMovimientoDto(MovimientoCaja movimiento) {
        return new MovimientoCajaDto(movimiento.getId(), movimiento.getCajaId(), movimiento.getRestauranteId(),
            movimiento.getTipo(), movimiento.getConcepto(), movimiento.getValor(), movimiento.getOrigen(),
            movimiento.getUsuarioId(), movimiento.getFechaHora());
    }

    private CuadreDto toCuadreDto(Cuadre cuadre) {
        return new CuadreDto(cuadre.getId(), cuadre.getCajaId(), cuadre.getBase(), cuadre.getVentasEfectivo(),
            cuadre.getTransferencias(), cuadre.getFiados(), cuadre.getGastos(), cuadre.getEfectivoEsperado(),
            cuadre.getEfectivoReal(), cuadre.getDiferencia(), cuadre.getUsuarioId(),
            cuadre.getFechaHora(), cuadre.getStatus());
    }

    private CajaDto toDto(Caja caja) { return new CajaDto(caja.getId(), caja.getRestauranteId(), caja.getBaseInicial(), caja.getFechaApertura(), caja.getStatus()); }
}
