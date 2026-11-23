package com.giro.backend.menus;

import java.math.BigDecimal;
import java.util.UUID;

public record OpcionMenuDto(UUID id, UUID productoId, UUID categoriaId, String name,
                            BigDecimal price, Integer orden, MenuStatus status) {
}
