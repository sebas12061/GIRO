package com.giro.backend.productos;

import java.math.BigDecimal;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-12T13:53:35-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.12 (Oracle Corporation)"
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
