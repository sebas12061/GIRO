package com.giro.backend.pedidos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoMapper mapper;
    private final PedidoStateMachine stateMachine;

    public PedidoService(PedidoRepository repository, PedidoMapper mapper, PedidoStateMachine stateMachine) {
        this.repository = repository;
        this.mapper = mapper;
        this.stateMachine = stateMachine;
    }

    public PedidoDto crear(CrearPedidoRequest request) {
        Pedido pedido = repository.save(new Pedido(request.restauranteId(), request.numero(), request.total()));
        return mapper.toDto(pedido);
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
        Pedido pedido = repository.findByIdAndRestauranteId(pedidoId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Pedido no encontrado para el restaurante"));

        stateMachine.validar(pedido.getEstado(), nuevoEstado);
        pedido.cambiarEstado(nuevoEstado);
        return mapper.toDto(repository.save(pedido));
    }
}
