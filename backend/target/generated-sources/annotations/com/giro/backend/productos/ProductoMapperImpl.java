package com.giro.backend.productos;

import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-17T15:13:59-0500",
    comments = "version: 1.6.3, compiler: Eclipse JDT (IDE) 3.46.200.v20260930-0211, environment: Java 25.0.4.1 (Eclipse Adoptium)"
)
@Component
public class ProductoMapperImpl implements ProductoMapper {

    @Override
    public ProductoDto toDto(Producto producto) {
        if ( producto == null ) {
            return null;
        }

        UUID categoriaId = null;
        UUID unidadId = null;
        UUID id = null;
        UUID restauranteId = null;
        String name = null;
        BigDecimal price = null;
        ProductoStatus status = null;

        categoriaId = productoCategoriaId( producto );
        unidadId = productoUnidadId( producto );
        id = producto.getId();
        restauranteId = producto.getRestauranteId();
        name = producto.getName();
        price = producto.getPrice();
        status = producto.getStatus();

        ProductoDto productoDto = new ProductoDto( id, restauranteId, name, price, categoriaId, unidadId, status );

        return productoDto;
    }

    private UUID productoCategoriaId(Producto producto) {
        Categoria categoria = producto.getCategoria();
        if ( categoria == null ) {
            return null;
        }
        return categoria.getId();
    }

    private UUID productoUnidadId(Producto producto) {
        UnidadOperativa unidad = producto.getUnidad();
        if ( unidad == null ) {
            return null;
        }
        return unidad.getId();
    }
}
