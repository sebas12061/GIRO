package com.giro.backend.caja;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cajas")
public class Caja {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "restaurante_id", nullable = false) private UUID restauranteId;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal baseInicial;
    @Column(nullable = false) private OffsetDateTime fechaApertura;
    @Column private OffsetDateTime fechaCierre;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private CajaStatus status = CajaStatus.OPEN;
    protected Caja() { }
    public Caja(UUID restauranteId, BigDecimal baseInicial) { this.restauranteId = restauranteId; this.baseInicial = baseInicial; this.fechaApertura = OffsetDateTime.now(); }
    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public BigDecimal getBaseInicial() { return baseInicial; }
    public OffsetDateTime getFechaApertura() { return fechaApertura; }
    public CajaStatus getStatus() { return status; }
    public void cerrar() { if (status == CajaStatus.CLOSED) throw new IllegalStateException("La caja ya esta cerrada"); status = CajaStatus.CLOSED; fechaCierre = OffsetDateTime.now(); }
}
