package com.giro.backend.productos;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "unidadId", source = "unidad.id")
    ProductoDto toDto(Producto producto);
}
