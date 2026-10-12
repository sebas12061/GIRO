package com.giro.backend.productos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, UUID> {
    List<Producto> findAllByRestauranteIdAndStatusOrderByName(UUID restauranteId, ProductoStatus status);
}
