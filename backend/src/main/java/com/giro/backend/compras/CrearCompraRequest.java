package com.giro.backend.compras;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CrearCompraRequest(
    @NotNull UUID restauranteId,
    @NotNull UUID proveedorId,
    UUID cajaId,
    String observacion,
    @NotEmpty @Valid List<CrearDetalleCompraRequest> detalles
) {
}
