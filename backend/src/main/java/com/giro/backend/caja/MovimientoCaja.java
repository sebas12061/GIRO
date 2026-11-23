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
@Table(name = "movimientos_caja")
public class MovimientoCaja {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "caja_id", nullable = false)
    private UUID cajaId;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimientoCaja tipo;

    @Column(nullable = false, length = 180)
    private String concepto;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(length = 30)
    private String origen;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    protected MovimientoCaja() {
    }

    public MovimientoCaja(UUID cajaId, UUID restauranteId, TipoMovimientoCaja tipo,
                          String concepto, BigDecimal valor, String origen, UUID usuarioId) {
        this.cajaId = cajaId;
        this.restauranteId = restauranteId;
        this.tipo = tipo;
        this.concepto = concepto;
        this.valor = valor;
        this.origen = origen;
        this.usuarioId = usuarioId;
    }

    public UUID getId() { return id; }
    public UUID getCajaId() { return cajaId; }
    public UUID getRestauranteId() { return restauranteId; }
    public TipoMovimientoCaja getTipo() { return tipo; }
    public String getConcepto() { return concepto; }
    public BigDecimal getValor() { return valor; }
    public String getOrigen() { return origen; }
    public UUID getUsuarioId() { return usuarioId; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
}
