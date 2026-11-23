package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.productos.Producto;
import com.giro.backend.productos.ProductoRepository;
import com.giro.backend.productos.ProductoStatus;

@Service
@Transactional
public class InventarioService {

    private final MovimientoProductoRepository movimientoRepository;
    private final CierreOperativoRepository cierreRepository;
    private final ProductoRepository productoRepository;

    public InventarioService(MovimientoProductoRepository movimientoRepository,
                             CierreOperativoRepository cierreRepository,
                             ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.cierreRepository = cierreRepository;
        this.productoRepository = productoRepository;
    }

    public MovimientoProductoDto registrarMovimiento(RegistrarMovimientoProductoRequest request) {
        Producto producto = productoRepository.findById(request.productoId())
            .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
            .filter(value -> value.getStatus() == ProductoStatus.ACTIVE)
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado o inactivo para el restaurante"));

        if (!producto.getUnidad().getId().equals(request.unidadId())) {
            throw new IllegalArgumentException("La unidad no corresponde al producto");
        }

        MovimientoProducto movimiento = movimientoRepository.save(new MovimientoProducto(
            request.restauranteId(), producto.getId(), request.unidadId(), request.fecha(),
            request.tipoMovimiento(), request.cantidad(), request.referencia(),
            request.usuarioId(), request.observacion()));
        return toMovimientoDto(movimiento);
    }

    @Transactional(readOnly = true)
    public List<MovimientoProductoDto> movimientos(UUID restauranteId, UUID productoId, LocalDate fecha) {
        productoRepository.findById(productoId)
            .filter(value -> value.getRestauranteId().equals(restauranteId))
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado para el restaurante"));
        return movimientoRepository.findAllByRestauranteIdAndProductoIdAndFechaOrderByFechaHoraAsc(
                restauranteId, productoId, fecha)
            .stream()
            .map(this::toMovimientoDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public ExistenciaDto calcularExistencia(UUID restauranteId, UUID productoId, LocalDate fecha) {
        Producto producto = productoRepository.findById(productoId)
            .filter(value -> value.getRestauranteId().equals(restauranteId))
            .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado para el restaurante"));

        List<MovimientoProducto> movimientos = movimientoRepository
            .findAllByRestauranteIdAndProductoIdAndFechaOrderByFechaHoraAsc(restauranteId, productoId, fecha);

        BigDecimal amanece = sumar(movimientos, TipoMovimientoProducto.AMANECE);
        BigDecimal llega = sumar(movimientos, TipoMovimientoProducto.LLEGA);
        BigDecimal desayuno = sumar(movimientos, TipoMovimientoProducto.FACT_DESA);
        BigDecimal almuerzo = sumar(movimientos, TipoMovimientoProducto.FACT_ALMU);
        BigDecimal personal = sumar(movimientos, TipoMovimientoProducto.REST);
        BigDecimal gastado = sumar(movimientos, TipoMovimientoProducto.G);
        BigDecimal ajuste = sumar(movimientos, TipoMovimientoProducto.AJUSTE);

        BigDecimal existencia = amanece.add(llega).add(ajuste)
            .subtract(desayuno).subtract(almuerzo).subtract(personal).subtract(gastado);

        return new ExistenciaDto(productoId, producto.getUnidad().getId(), fecha,
            amanece, llega, desayuno, almuerzo, personal, gastado, ajuste, existencia);
    }

    public CierreOperativoDto cerrar(CerrarInventarioRequest request) {
        cierreRepository.findByRestauranteIdAndFecha(request.restauranteId(), request.fecha())
            .ifPresent(cierre -> {
                throw new IllegalStateException("Ya existe un cierre operativo para esa fecha y restaurante");
            });

        CierreOperativo cierre = new CierreOperativo(request.restauranteId(), request.fecha(), request.usuarioId());
        request.detalles().forEach(detalleRequest -> {
            Producto producto = productoRepository.findById(detalleRequest.productoId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado para el restaurante"));
            if (!producto.getUnidad().getId().equals(detalleRequest.unidadId())) {
                throw new IllegalArgumentException("La unidad no corresponde al producto");
            }
            cierre.agregarDetalle(new DetalleCierreOperativo(producto.getId(), detalleRequest.cantidadQueda(),
                detalleRequest.unidadId(), detalleRequest.observacion()));
        });

        cierre.cerrar();
        CierreOperativo saved = cierreRepository.save(cierre);
        return toCierreDto(saved);
    }

    private BigDecimal sumar(List<MovimientoProducto> movimientos, TipoMovimientoProducto tipo) {
        return movimientos.stream()
            .filter(movimiento -> movimiento.getTipoMovimiento() == tipo)
            .map(MovimientoProducto::getCantidad)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private MovimientoProductoDto toMovimientoDto(MovimientoProducto movimiento) {
        return new MovimientoProductoDto(movimiento.getId(), movimiento.getRestauranteId(),
            movimiento.getProductoId(), movimiento.getUnidadId(), movimiento.getFecha(),
            movimiento.getTipoMovimiento(), movimiento.getCantidad(), movimiento.getReferencia(),
            movimiento.getUsuarioId(), movimiento.getObservacion(), movimiento.getFechaHora());
    }

    private CierreOperativoDto toCierreDto(CierreOperativo cierre) {
        List<DetalleCierreOperativoDto> detalles = cierre.getDetalles().stream()
            .map(detalle -> new DetalleCierreOperativoDto(detalle.getId(), detalle.getProductoId(),
                detalle.getCantidadQueda(), detalle.getUnidadId(), detalle.getObservacion()))
            .toList();
        return new CierreOperativoDto(cierre.getId(), cierre.getRestauranteId(), cierre.getFecha(),
            cierre.getUsuarioId(), cierre.getStatus(), detalles);
    }
}
