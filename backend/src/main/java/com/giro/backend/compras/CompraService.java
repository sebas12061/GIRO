package com.giro.backend.compras;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giro.backend.caja.Caja;
import com.giro.backend.caja.CajaRepository;
import com.giro.backend.caja.CajaStatus;
import com.giro.backend.caja.MovimientoCajaRepository;
import com.giro.backend.caja.MovimientoCaja;
import com.giro.backend.caja.TipoMovimientoCaja;
import com.giro.backend.productos.Producto;
import com.giro.backend.productos.ProductoRepository;
import com.giro.backend.productos.ProductoStatus;
import com.giro.backend.inventario.MovimientoProducto;
import com.giro.backend.inventario.MovimientoProductoRepository;
import com.giro.backend.inventario.TipoMovimientoProducto;

@Service
@Transactional
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorRepository proveedorRepository;
    private final ProductoRepository productoRepository;
    private final CajaRepository cajaRepository;
    private final MovimientoCajaRepository movimientoCajaRepository;
    private final MovimientoProductoRepository movimientoProductoRepository;

    public CompraService(CompraRepository compraRepository, ProveedorRepository proveedorRepository,
                         ProductoRepository productoRepository, CajaRepository cajaRepository,
                         MovimientoCajaRepository movimientoCajaRepository,
                         MovimientoProductoRepository movimientoProductoRepository) {
        this.compraRepository = compraRepository;
        this.proveedorRepository = proveedorRepository;
        this.productoRepository = productoRepository;
        this.cajaRepository = cajaRepository;
        this.movimientoCajaRepository = movimientoCajaRepository;
        this.movimientoProductoRepository = movimientoProductoRepository;
    }

    public CompraDto registrar(CrearCompraRequest request) {
        Proveedor proveedor = proveedorRepository.findByIdAndRestauranteId(request.proveedorId(), request.restauranteId())
            .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado para el restaurante"));
        if (proveedor.getStatus() != ProveedorStatus.ACTIVE) {
            throw new IllegalStateException("No se pueden registrar compras a proveedores inactivos");
        }

        Caja caja = null;
        if (request.cajaId() != null) {
            caja = cajaRepository.findById(request.cajaId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .filter(value -> value.getStatus() == CajaStatus.OPEN)
                .orElseThrow(() -> new IllegalArgumentException("La caja indicada no existe, no pertenece al restaurante o esta cerrada"));
        }

        Compra compra = new Compra(request.restauranteId(), proveedor.getId(),
            request.cajaId(), request.observacion());

        request.detalles().forEach(detalleRequest -> {
            Producto producto = productoRepository.findById(detalleRequest.productoId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .filter(value -> value.getStatus() == ProductoStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado o inactivo para el restaurante"));
            compra.agregarDetalle(new DetalleCompra(producto.getId(), detalleRequest.cantidad(), detalleRequest.valorUnitario()));
        });

        Compra registrada = compraRepository.save(compra);

        for (DetalleCompra detalle : registrada.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado al integrar compra"));
            movimientoProductoRepository.save(new MovimientoProducto(
                registrada.getRestauranteId(), producto.getId(), producto.getUnidad().getId(),
                java.time.LocalDate.now(), TipoMovimientoProducto.LLEGA, detalle.getCantidad(),
                "COMPRA:" + registrada.getId(), null, "Entrada recibida por compra"));
        }

        if (caja != null) {
            movimientoCajaRepository.save(new MovimientoCaja(
                caja.getId(), request.restauranteId(), TipoMovimientoCaja.GASTO,
                "Compra a proveedor " + proveedor.getName(), registrada.getTotal(),
                "COMPRA:" + registrada.getId(), null));
        }

        return toDto(registrada);
    }

    @Transactional(readOnly = true)
    public Page<CompraDto> listar(UUID restauranteId, Pageable pageable) {
        return compraRepository.findAllByRestauranteId(restauranteId, pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<CompraDto> listar(UUID restauranteId) {
        return compraRepository.findAllByRestauranteIdOrderByFechaDesc(restauranteId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public CompraDto anular(UUID restauranteId, UUID compraId) {
        Compra compra = compraRepository.findByIdAndRestauranteId(compraId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Compra no encontrada para el restaurante"));

        compra.anular();

        for (DetalleCompra detalle : compra.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado al anular compra"));
            movimientoProductoRepository.save(new MovimientoProducto(
                restauranteId, producto.getId(), producto.getUnidad().getId(),
                java.time.LocalDate.now(), TipoMovimientoProducto.AJUSTE,
                detalle.getCantidad().negate(), "COMPRA_ANULADA:" + compra.getId(),
                null, "Reversion de entrada por anulacion de compra"));
        }

        if (compra.getCajaId() != null) {
            String origenAnulada = "COMPRA_ANULADA:" + compra.getId();
            boolean yaRevertida = !movimientoCajaRepository
                .findAllByRestauranteIdAndOrigen(restauranteId, origenAnulada).isEmpty();
            if (!yaRevertida) {
                movimientoCajaRepository.save(new MovimientoCaja(
                    compra.getCajaId(), restauranteId, TipoMovimientoCaja.INGRESO,
                    "Reversion de compra anulada", compra.getTotal(), origenAnulada, null));
            }
        }

        return toDto(compraRepository.save(compra));
    }

    private CompraDto toDto(Compra compra) {
        List<DetalleCompraDto> detalles = compra.getDetalles().stream()
            .map(detalle -> new DetalleCompraDto(detalle.getId(), detalle.getProductoId(), detalle.getCantidad(),
                detalle.getValorUnitario(), detalle.getSubtotal()))
            .toList();
        return new CompraDto(compra.getId(), compra.getRestauranteId(), compra.getProveedorId(), compra.getCajaId(),
            compra.getFecha(), compra.getTotal(), compra.getStatus(), compra.getObservacion(), detalles);
    }
}
