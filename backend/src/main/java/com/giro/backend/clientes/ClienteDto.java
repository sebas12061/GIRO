package com.giro.backend.clientes;

import java.util.UUID;

public record ClienteDto(
    UUID id,
    UUID restauranteId,
    String name,
    String telefono,
    String direccionReferencia,
    TipoCliente tipoCliente,
    ClienteStatus status
) {
}
