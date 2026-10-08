package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-07T21:20:23-0500",
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

        id = pedido.getId();
        restauranteId = pedido.getRestauranteId();
        numero = pedido.getNumero();
        estado = pedido.getEstado();
        total = pedido.getTotal();
        fechaHora = pedido.getFechaHora();

        PedidoDto pedidoDto = new PedidoDto( id, restauranteId, numero, estado, total, fechaHora );

        return pedidoDto;
    }
}
