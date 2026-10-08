package com.giro.backend.auditoria;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AuditEvent(
    UUID restauranteId,
    UUID usuarioId,
    String action,
    String entity,
    UUID entityId,
    String oldValue,
    String newValue,
    OffsetDateTime occurredAt
) {
}
