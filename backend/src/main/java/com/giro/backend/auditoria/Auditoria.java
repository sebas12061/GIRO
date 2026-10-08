package com.giro.backend.auditoria;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "auditoria")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id")
    private UUID restauranteId;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(nullable = false, length = 80)
    private String action;

    @Column(nullable = false, length = 80)
    private String entity;

    @Column(name = "entity_id")
    private UUID entityId;

    @Column(name = "old_value", length = 2000)
    private String oldValue;

    @Column(name = "new_value", length = 2000)
    private String newValue;

    @Column(nullable = false)
    private OffsetDateTime occurredAt;

    protected Auditoria() {
    }

    public Auditoria(AuditEvent event) {
        this.restauranteId = event.restauranteId();
        this.usuarioId = event.usuarioId();
        this.action = event.action();
        this.entity = event.entity();
        this.entityId = event.entityId();
        this.oldValue = event.oldValue();
        this.newValue = event.newValue();
        this.occurredAt = event.occurredAt();
    }
}
