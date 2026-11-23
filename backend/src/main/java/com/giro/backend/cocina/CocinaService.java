package com.giro.backend.cocina;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.pedidos.PedidoDto;
import com.giro.backend.pedidos.PedidoEstado;
import com.giro.backend.pedidos.PedidoMapper;
import com.giro.backend.pedidos.PedidoRepository;
import com.giro.backend.pedidos.PedidoService;

@Service
@Transactional
public class CocinaService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final PedidoService pedidoService;

    public CocinaService(PedidoRepository pedidoRepository, PedidoMapper pedidoMapper, PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
        this.pedidoMapper = pedidoMapper;
        this.pedidoService = pedidoService;
    }

    @Transactional(readOnly = true)
    public List<PedidoDto> listarPendientes(UUID restauranteId) {
        List<PedidoEstado> estados = List.of(
            PedidoEstado.CREADO,
            PedidoEstado.CONFIRMADO,
            PedidoEstado.EN_PREPARACION
        );
        return pedidoRepository.findAllByRestauranteIdAndEstadoInOrderByFechaHoraAsc(restauranteId, estados)
            .stream()
            .map(pedidoMapper::toDto)
            .toList();
    }

    public PedidoDto confirmarPedido(UUID restauranteId, UUID pedidoId) {
        return pedidoService.cambiarEstado(restauranteId, pedidoId, PedidoEstado.CONFIRMADO);
    }

    public PedidoDto iniciarPreparacion(UUID restauranteId, UUID pedidoId) {
        return pedidoService.cambiarEstado(restauranteId, pedidoId, PedidoEstado.EN_PREPARACION);
    }

    public PedidoDto marcarPreparado(UUID restauranteId, UUID pedidoId) {
        return pedidoService.cambiarEstado(restauranteId, pedidoId, PedidoEstado.PREPARADO);
    }
}
