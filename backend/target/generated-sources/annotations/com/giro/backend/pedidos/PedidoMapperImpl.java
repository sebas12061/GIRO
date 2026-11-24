package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-23T19:08:36-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12 (Oracle Corporation)"
)
@Component
public class PedidoMapperImpl implements PedidoMapper {

    @Override
    public PedidoDto toDto(Pedido pedido) {
        if ( pedido == null ) {
            return null;
        }

        UUID id = null;
        UUID restauranteId = null;
        Long numero = null;
        PedidoEstado estado = null;
        BigDecimal total = null;
        OffsetDateTime fechaHora = null;
        UUID mesaId = null;
        UUID clienteId = null;
        TipoServicio tipoServicio = null;
        String observaciones = null;
        List<DetallePedidoDto> detalles = null;

        id = pedido.getId();
        restauranteId = pedido.getRestauranteId();
        numero = pedido.getNumero();
        estado = pedido.getEstado();
        total = pedido.getTotal();
        fechaHora = pedido.getFechaHora();
        mesaId = pedido.getMesaId();
        clienteId = pedido.getClienteId();
        tipoServicio = pedido.getTipoServicio();
        observaciones = pedido.getObservaciones();
        detalles = detallePedidoListToDetallePedidoDtoList( pedido.getDetalles() );

        PedidoDto pedidoDto = new PedidoDto( id, restauranteId, numero, estado, total, fechaHora, mesaId, clienteId, tipoServicio, observaciones, detalles );

        return pedidoDto;
    }

    @Override
    public DetallePedidoDto toDetalleDto(DetallePedido detalle) {
        if ( detalle == null ) {
            return null;
        }

        UUID id = null;
        UUID productoId = null;
        UUID opcionMenuId = null;
        BigDecimal cantidad = null;
        BigDecimal precioUnitario = null;
        BigDecimal subtotal = null;
        String observaciones = null;
        DetallePedidoEstado estado = null;

        id = detalle.getId();
        productoId = detalle.getProductoId();
        opcionMenuId = detalle.getOpcionMenuId();
        cantidad = detalle.getCantidad();
        precioUnitario = detalle.getPrecioUnitario();
        subtotal = detalle.getSubtotal();
        observaciones = detalle.getObservaciones();
        estado = detalle.getEstado();

        DetallePedidoDto detallePedidoDto = new DetallePedidoDto( id, productoId, opcionMenuId, cantidad, precioUnitario, subtotal, observaciones, estado );

        return detallePedidoDto;
    }

    protected List<DetallePedidoDto> detallePedidoListToDetallePedidoDtoList(List<DetallePedido> list) {
        if ( list == null ) {
            return null;
        }

        List<DetallePedidoDto> list1 = new ArrayList<DetallePedidoDto>( list.size() );
        for ( DetallePedido detallePedido : list ) {
            list1.add( toDetalleDto( detallePedido ) );
        }

        return list1;
    }
}
