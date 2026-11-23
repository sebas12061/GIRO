package com.giro.backend.clientes;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearClienteRequest(
    @NotNull UUID restauranteId,
    @NotBlank String name,
    String telefono,
    String direccionReferencia,
    @NotNull TipoCliente tipoCliente
) {
}
