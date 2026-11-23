package com.giro.backend.pedidos;

import java.util.UUID;

public record MesaDto(UUID id, UUID restauranteId, Integer numero, Integer capacidad, MesaEstado estado) {
}
