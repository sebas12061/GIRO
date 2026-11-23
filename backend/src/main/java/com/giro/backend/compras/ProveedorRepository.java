package com.giro.backend.compras;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, UUID> {
    Optional<Proveedor> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Proveedor> findAllByRestauranteIdAndStatusOrderByName(UUID restauranteId, ProveedorStatus status);
}
