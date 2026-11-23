package com.giro.backend.productos;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
    List<Categoria> findAllByRestauranteIdAndActivoTrueOrderByName(UUID restauranteId);
}
