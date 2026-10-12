package com.giro.backend.caja;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public record CajaDto(UUID id, UUID restauranteId, BigDecimal baseInicial, OffsetDateTime fechaApertura, CajaStatus status) { }
