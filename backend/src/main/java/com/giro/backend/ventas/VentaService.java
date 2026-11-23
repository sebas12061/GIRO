package com.giro.backend.ventas;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.pagos.PagoDto;
import com.giro.backend.pagos.PagoRepository;
import com.giro.backend.pagos.PagoService;
import com.giro.backend.pagos.PagoStatus;
import com.giro.backend.pedidos.Pedido;
import com.giro.backend.pedidos.PedidoEstado;
import com.giro.backend.pedidos.PedidoRepository;
import com.giro.backend.pedidos.PedidoService;
import com.giro.backend.productos.ProductoRepository;
import com.giro.backend.inventario.MovimientoProducto;
import com.giro.backend.inventario.TipoMovimientoProducto;
import com.giro.backend.inventario.MovimientoProductoRepository;

@Service
@Transactional
public class VentaService {

    private final VentaRepository ventaRepository;
    private final PedidoRepository pedidoRepository;
    private final PagoRepository pagoRepository;
    private final PagoService pagoService;
    private final PedidoService pedidoService;
    private final ProductoRepository productoRepository;
    private final MovimientoProductoRepository movimientoProductoRepository;

    public VentaService(VentaRepository ventaRepository, PedidoRepository pedidoRepository,
                        PagoRepository pagoRepository, PagoService pagoService,
                        PedidoService pedidoService, ProductoRepository productoRepository,
                        MovimientoProductoRepository movimientoProductoRepository) {
        this.ventaRepository = ventaRepository;
        this.pedidoRepository = pedidoRepository;
        this.pagoRepository = pagoRepository;
        this.pagoService = pagoService;
        this.pedidoService = pedidoService;
        this.productoRepository = productoRepository;
        this.movimientoProductoRepository = movimientoProductoRepository;
    }

    public VentaDto cobrar(CobrarRequest request) {
        Pedido pedido = pedidoRepository.findLockedByIdAndRestauranteId(request.pedidoId(), request.restauranteId())
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado para el restaurante"));

        if (pedido.getEstado() != PedidoEstado.PENDIENTE_PAGO) {
            throw new IllegalStateException("El pedido debe estar PENDIENTE_PAGO para generar cobro");
        }

        Venta venta = ventaRepository.findLockedByPedidoIdAndRestauranteId(pedido.getId(), request.restauranteId())
            .orElseGet(() -> ventaRepository.save(new Venta(request.restauranteId(), pedido.getId(),
                pedido.getTotal(), pedido.getTotal())));

        if (venta.getStatus() == VentaStatus.PAGADA) {
            throw new IllegalStateException("La venta ya fue pagada");
        }

        List<PagoDto> pagosRegistrados = request.pagos().stream()
            .map(pagoRequest -> {
                if (!pagoRequest.restauranteId().equals(request.restauranteId())
                    || !pagoRequest.ventaId().equals(venta.getId())) {
                    throw new IllegalArgumentException("Los pagos deben pertenecer al restaurante y a la venta solicitados");
                }
                return pagoService.registrar(pagoRequest);
            })
            .toList();

        BigDecimal confirmado = pagosRegistrados.stream()
            .filter(pago -> pago.status() == PagoStatus.CONFIRMED)
            .map(PagoDto::valor)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (confirmado.compareTo(venta.getTotal()) >= 0) {
            venta.pagar();
            ventaRepository.save(venta);

            pedido.getDetalles().forEach(detalle -> {
                var producto = productoRepository.findById(detalle.getProductoId())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado al consumir inventario"));

                java.math.BigDecimal existencia = movimientoProductoRepository
                    .findAllByRestauranteIdAndProductoId(request.restauranteId(), producto.getId())
                    .stream()
                    .map(movimiento -> switch (movimiento.getTipoMovimiento()) {
                        case AMANECE, LLEGA, AJUSTE -> movimiento.getCantidad();
                        case FACT_DESA, FACT_ALMU, REST, G -> movimiento.getCantidad().negate();
                    })
                    .reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add);

                if (existencia.subtract(detalle.getCantidad()).signum() < 0) {
                    throw new IllegalStateException("Stock insuficiente para el producto: " + producto.getName());
                }
                movimientoProductoRepository.save(new MovimientoProducto(
                    request.restauranteId(), producto.getId(), producto.getUnidad().getId(),
                    java.time.LocalDate.now(), TipoMovimientoProducto.G, detalle.getCantidad(),
                    "VENTA:" + venta.getId(), null, "Consumo generado por venta pagada"));
            });

            pedidoService.cambiarEstado(request.restauranteId(), pedido.getId(), PedidoEstado.PAGADO);
            pedidoService.cambiarEstado(request.restauranteId(), pedido.getId(), PedidoEstado.CERRADO);
        }

        List<PagoDto> pagos = pagoRepository
            .findAllByVentaIdAndRestauranteIdOrderByFechaHora(venta.getId(), request.restauranteId())
            .stream()
            .map(pago -> new PagoDto(pago.getId(), pago.getRestauranteId(), pago.getVentaId(), pago.getTipo(),
                pago.getValor(), pago.getReferencia(), pago.getStatus(), pago.getFechaHora()))
            .toList();

        return toDto(venta, pagos);
    }

    private VentaDto toDto(Venta venta, List<PagoDto> pagos) {
        return new VentaDto(venta.getId(), venta.getRestauranteId(), venta.getPedidoId(),
            venta.getSubtotal(), venta.getTotal(), venta.getStatus(), venta.getFechaHora(), pagos);
    }
}
