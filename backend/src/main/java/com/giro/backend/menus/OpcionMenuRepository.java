package com.giro.backend.menus;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpcionMenuRepository extends JpaRepository<OpcionMenu, UUID> {
}
