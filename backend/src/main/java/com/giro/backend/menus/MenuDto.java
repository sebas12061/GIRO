package com.giro.backend.menus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record MenuDto(UUID id, UUID restauranteId, String name, LocalDate fechaInicio,
                      LocalDate fechaFin, MenuStatus status, List<OpcionMenuDto> opciones) {
}
