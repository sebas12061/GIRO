package com.giro.backend.pedidos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.giro.backend.productos.Producto;
import com.giro.backend.productos.ProductoRepository;
import com.giro.backend.productos.ProductoStatus;
import com.giro.backend.menus.MenuStatus;
import com.giro.backend.menus.OpcionMenu;
import com.giro.backend.menus.OpcionMenuRepository;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final PedidoStateMachine stateMachine;
    private final MesaRepository mesaRepository;
    private final ProductoRepository productoRepository;
    private final OpcionMenuRepository opcionMenuRepository;

    public PedidoService(PedidoRepository repository, PedidoMapper mapper, PedidoStateMachine stateMachine,
                         MesaRepository mesaRepository, ProductoRepository productoRepository,
                         OpcionMenuRepository opcionMenuRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.stateMachine = stateMachine;
        this.mesaRepository = mesaRepository;
        this.productoRepository = productoRepository;
        this.opcionMenuRepository = opcionMenuRepository;
    }

    public PedidoDto crear(CrearPedidoRequest request) {
        Mesa mesa = null;
        if (request.tipoServicio() == TipoServicio.MESA) {
            if (request.mesaId() == null) {
                throw new IllegalArgumentException("Un pedido de mesa requiere mesaId");
            }
            mesa = mesaRepository.findByIdAndRestauranteId(request.mesaId(), request.restauranteId())
                .orElseThrow(() -> new IllegalArgumentException("Mesa no encontrada para el restaurante"));
            boolean tienePedidoActivo = repository.findAllByRestauranteIdAndMesaId(request.restauranteId(), mesa.getId())
                .stream()
                .anyMatch(pedido -> pedido.getEstado() != PedidoEstado.CERRADO
                    && pedido.getEstado() != PedidoEstado.CANCELADO);
            if (tienePedidoActivo) {
                throw new IllegalStateException("La mesa ya tiene un pedido activo");
            }
        }

        Pedido pedido = new Pedido(request.restauranteId(), request.numero(), request.mesaId(), request.clienteId(),
            request.tipoServicio(), request.observaciones());
        request.detalles().forEach(detalleRequest -> {
            Producto producto = productoRepository.findById(detalleRequest.productoId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .filter(value -> value.getStatus() == ProductoStatus.ACTIVE)
                .orElseThrow(() -> new IllegalArgumentException(
                    "Producto no encontrado, inactivo o ajeno al restaurante"));
            java.math.BigDecimal precio = producto.getPrice();
            if (detalleRequest.opcionMenuId() != null) {
                OpcionMenu opcion = opcionMenuRepository.findById(detalleRequest.opcionMenuId())
                    .filter(value -> value.getStatus() == MenuStatus.ACTIVE)
                    .orElseThrow(() -> new IllegalArgumentException("Opcion de menu no encontrada o inactiva"));
                if (opcion.getProductoId() != null
                    && !opcion.getProductoId().equals(producto.getId())) {
                    throw new IllegalArgumentException("La opcion no corresponde al producto");
                }
                precio = opcion.getPrice();
            }
            pedido.agregarDetalle(new DetallePedido(
                producto.getId(), detalleRequest.opcionMenuId(), detalleRequest.cantidad(),
                precio, detalleRequest.observaciones()));
        });

        Pedido saved = repository.save(pedido);
        if (mesa != null) {
            mesa.ocupar();
            mesaRepository.save(mesa);
        }
        return mapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public Page<PedidoDto> listar(UUID restauranteId, Pageable pageable) {
        return repository.findAllByRestauranteId(restauranteId, pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> listar(UUID restauranteId) {
        return repository.findAllByRestauranteIdOrderByFechaHoraDesc(restauranteId)
            .stream()
            .map(mapper::toDto)
            .toList();
    }

    @PedidoAudit(action = "CAMBIAR_ESTADO_PEDIDO")
    public PedidoDto cambiarEstado(UUID restauranteId, UUID pedidoId, PedidoEstado nuevoEstado) {
        Pedido pedido = repository.findLockedByIdAndRestauranteId(pedidoId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado para el restaurante"));

        stateMachine.validar(pedido.getEstado(), nuevoEstado);
        pedido.cambiarEstado(nuevoEstado);
        Pedido saved = repository.save(pedido);

        if (nuevoEstado == PedidoEstado.CERRADO || nuevoEstado == PedidoEstado.CANCELADO) {
            liberarMesa(restauranteId, pedido);
        }
        return mapper.toDto(saved);
    }

    @PedidoAudit(action = "EDITAR_PEDIDO_ABIERTO")
    public PedidoDto editarDetalles(UUID restauranteId, UUID pedidoId, EditarPedidoRequest request) {
        Pedido pedido = repository.findLockedByIdAndRestauranteId(pedidoId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado para el restaurante"));
        if (!pedidoAbierto(pedido)) {
            throw new IllegalStateException("Solo se pueden editar pedidos abiertos");
        }

        List<DetallePedido> detalles = request.detalles().stream()
            .map(detalleRequest -> {
                Producto producto = productoRepository.findById(detalleRequest.productoId())
                    .filter(value -> value.getRestauranteId().equals(restauranteId))
                    .filter(value -> value.getStatus() == ProductoStatus.ACTIVE)
                    .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado, inactivo o ajeno al restaurante"));
                java.math.BigDecimal precio = producto.getPrice();
                if (detalleRequest.opcionMenuId() != null) {
                    OpcionMenu opcion = opcionMenuRepository.findById(detalleRequest.opcionMenuId())
                        .filter(value -> value.getStatus() == MenuStatus.ACTIVE)
                        .orElseThrow(() -> new IllegalArgumentException("Opcion de menu no encontrada o inactiva"));
                    if (opcion.getProductoId() != null
                        && !opcion.getProductoId().equals(producto.getId())) {
                        throw new IllegalArgumentException("La opcion no corresponde al producto");
                    }
                    precio = opcion.getPrice();
                }
                return new DetallePedido(producto.getId(), detalleRequest.opcionMenuId(),
                    detalleRequest.cantidad(), precio, detalleRequest.observaciones());
            })
            .toList();
        pedido.reemplazarDetalles(detalles);
        return mapper.toDto(repository.save(pedido));
    }

    private boolean pedidoAbierto(Pedido pedido) {
        return pedido.getEstado() != PedidoEstado.PAGADO
            && pedido.getEstado() != PedidoEstado.CERRADO
            && pedido.getEstado() != PedidoEstado.CANCELADO;
    }

    private void liberarMesa(UUID restauranteId, Pedido pedido) {
        if (pedido.getMesaId() != null) {
            mesaRepository.findByIdAndRestauranteId(pedido.getMesaId(), restauranteId)
                .ifPresent(mesa -> {
                    mesa.liberar();
                    mesaRepository.save(mesa);
                });
        }
    }
}
