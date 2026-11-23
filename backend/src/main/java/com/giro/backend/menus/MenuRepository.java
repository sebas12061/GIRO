package com.giro.backend.menus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
    Optional<Menu> findByIdAndRestauranteId(UUID id, UUID restauranteId);
    List<Menu> findAllByRestauranteIdAndStatusOrderByName(UUID restauranteId, MenuStatus status);
}
