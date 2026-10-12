package com.giro.backend.productos;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductoDto(UUID id, UUID restauranteId, String name, BigDecimal price, UUID categoriaId, UUID unidadId, ProductoStatus status) { }
