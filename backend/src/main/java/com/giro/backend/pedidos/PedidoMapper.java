package com.giro.backend.pedidos;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    PedidoDto toDto(Pedido pedido);
}
