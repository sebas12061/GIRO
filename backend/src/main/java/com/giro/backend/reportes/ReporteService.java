package com.giro.backend.reportes;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.compras.Compra;
import com.giro.backend.compras.CompraRepository;
import com.giro.backend.compras.CompraStatus;
import com.giro.backend.fiados.Fiado;
import com.giro.backend.fiados.FiadoRepository;
import com.giro.backend.fiados.FiadoStatus;
import com.giro.backend.pagos.Pago;
import com.giro.backend.pagos.PagoRepository;
import com.giro.backend.pagos.PagoStatus;
import com.giro.backend.pagos.TipoPago;
import com.giro.backend.ventas.Venta;
import com.giro.backend.ventas.VentaRepository;
import com.giro.backend.ventas.VentaStatus;
import com.giro.backend.caja.CajaRepository;
import com.giro.backend.caja.CuadreRepository;
import com.giro.backend.caja.CuadreStatus;
import com.giro.backend.personal.TurnoRepository;
import com.giro.backend.personal.TurnoStatus;

@Service
@Transactional(readOnly = true)
public class ReporteService {

    private final VentaRepository ventaRepository;
    private final PagoRepository pagoRepository;
    private final CompraRepository compraRepository;
    private final FiadoRepository fiadoRepository;
    private final CajaRepository cajaRepository;
    private final CuadreRepository cuadreRepository;
    private final TurnoRepository turnoRepository;

    public ReporteService(VentaRepository ventaRepository, PagoRepository pagoRepository,
                          CompraRepository compraRepository, FiadoRepository fiadoRepository,
                          CajaRepository cajaRepository, CuadreRepository cuadreRepository,
                          TurnoRepository turnoRepository) {
        this.ventaRepository = ventaRepository;
        this.pagoRepository = pagoRepository;
        this.compraRepository = compraRepository;
        this.fiadoRepository = fiadoRepository;
        this.cajaRepository = cajaRepository;
        this.cuadreRepository = cuadreRepository;
        this.turnoRepository = turnoRepository;
    }

    public ReporteOperativoDto operativo(ReportePeriodoRequest request) {
        if (request.hasta().isBefore(request.desde())) {
            throw new IllegalArgumentException("La fecha hasta debe ser igual o posterior a la fecha desde");
        }

        OffsetDateTime inicio = request.desde().atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fin = request.hasta().plusDays(1).atStartOfDay().atOffset(ZoneOffset.UTC);

        ResumenVentasDto resumenVentas = resumirVentas(request.restauranteId(), inicio, fin);
        ResumenComprasDto resumenCompras = resumirCompras(request.restauranteId(), inicio, fin);
        ResumenFiadosDto resumenFiados = resumirFiados(request.restauranteId());

        java.util.List<com.giro.backend.caja.Cuadre> cuadres = cajaRepository
            .findAllByRestauranteId(request.restauranteId()).stream()
            .flatMap(caja -> cuadreRepository.findAllByCajaId(caja.getId()).stream())
            .filter(cuadre -> cuadre.getStatus() == CuadreStatus.CLOSED)
            .filter(cuadre -> !cuadre.getFechaHora().isBefore(inicio)
                && cuadre.getFechaHora().isBefore(fin))
            .toList();
        BigDecimal diferenciaCaja = cuadres.stream()
            .map(com.giro.backend.caja.Cuadre::getDiferencia)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        java.util.List<com.giro.backend.personal.Turno> turnos = turnoRepository
            .findAllByRestauranteIdAndFechaBetween(request.restauranteId(), request.desde(), request.hasta());
        java.util.List<com.giro.backend.personal.Turno> finalizados = turnos.stream()
            .filter(turno -> turno.getStatus() == TurnoStatus.FINALIZADO)
            .toList();
        BigDecimal valorTurnos = finalizados.stream()
            .map(com.giro.backend.personal.Turno::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReporteOperativoDto(request.restauranteId(), request.desde(), request.hasta(),
            resumenVentas, resumenCompras, resumenFiados, cuadres.size(), diferenciaCaja,
            finalizados.size(), valorTurnos);
    }

    private ResumenVentasDto resumirVentas(java.util.UUID restauranteId,
                                           OffsetDateTime inicio, OffsetDateTime fin) {
        java.util.List<Venta> ventas = ventaRepository
            .findAllByRestauranteIdAndFechaHoraBetweenOrderByFechaHoraDesc(restauranteId, inicio, fin);
        java.util.List<Venta> pagadas = ventas.stream()
            .filter(venta -> venta.getStatus() == VentaStatus.PAGADA)
            .toList();

        BigDecimal totalVentas = pagadas.stream()
            .map(Venta::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal efectivo = sumarPagosPeriodo(restauranteId, inicio, fin, TipoPago.EFECTIVO);
        BigDecimal transferencias = sumarPagosPeriodo(restauranteId, inicio, fin, TipoPago.TRANSFERENCIA);
        BigDecimal fiados = sumarPagosPeriodo(restauranteId, inicio, fin, TipoPago.FIADO);

        return new ResumenVentasDto(pagadas.size(), totalVentas, efectivo, transferencias, fiados);
    }

    private BigDecimal sumarPagosPeriodo(java.util.UUID restauranteId, OffsetDateTime inicio,
                                         OffsetDateTime fin, TipoPago tipo) {
        return pagoRepository.findAllByRestauranteIdAndFechaHoraBetweenOrderByFechaHoraDesc(
                restauranteId, inicio, fin)
            .stream()
            .filter(pago -> pago.getTipo() == tipo)
            .filter(pago -> pago.getStatus() == PagoStatus.CONFIRMED)
            .map(Pago::getValor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private ResumenComprasDto resumirCompras(java.util.UUID restauranteId,
                                             OffsetDateTime inicio, OffsetDateTime fin) {
        java.util.List<Compra> compras = compraRepository
            .findAllByRestauranteIdAndFechaBetweenOrderByFechaDesc(restauranteId, inicio, fin);
        java.util.List<Compra> registradas = compras.stream()
            .filter(compra -> compra.getStatus() == CompraStatus.REGISTRADA)
            .toList();
        BigDecimal total = registradas.stream()
            .map(Compra::getTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ResumenComprasDto(registradas.size(), total);
    }

    private ResumenFiadosDto resumirFiados(java.util.UUID restauranteId) {
        java.util.List<Fiado> fiados = fiadoRepository.findAllByRestauranteIdOrderByFechaInicioDesc(restauranteId);
        long abiertos = fiados.stream().filter(fiado -> fiado.getStatus() == FiadoStatus.ABIERTO).count();
        long pagados = fiados.stream().filter(fiado -> fiado.getStatus() == FiadoStatus.PAGADO).count();
        long vencidos = fiados.stream().filter(fiado -> fiado.getStatus() == FiadoStatus.VENCIDO).count();
        BigDecimal saldo = fiados.stream()
            .filter(fiado -> fiado.getStatus() == FiadoStatus.ABIERTO)
            .map(Fiado::getSaldo)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new ResumenFiadosDto(abiertos, pagados, vencidos, saldo);
    }
}
