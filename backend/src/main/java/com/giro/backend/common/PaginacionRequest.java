package com.giro.backend.common;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PaginacionRequest(
    @PositiveOrZero Integer page,
    @Positive @Max(100) Integer size,
    String sort
) {
    public PaginacionRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
        if (sort == null || sort.isBlank()) sort = "fechaHora,DESC";
    }
}
