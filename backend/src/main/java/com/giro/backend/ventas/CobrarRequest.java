package com.giro.backend.ventas;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import com.giro.backend.pagos.RegistrarPagoRequest;

public record CobrarRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID pedidoId,
    @NotEmpty @Valid List<RegistrarPagoRequest> pagos
) {
}
