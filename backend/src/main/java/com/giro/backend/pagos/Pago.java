package com.giro.backend.pagos;

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
@Table(name = "pagos")
public class Pago {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "restaurante_id", nullable = false) private UUID restauranteId;
    @Column(name = "venta_id", nullable = false) private UUID ventaId;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private TipoPago tipo;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal valor;
    @Column(length = 120) private String referencia;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 30) private PagoStatus status;
    @Column(nullable = false) private OffsetDateTime fechaHora;
    protected Pago() { }
    public Pago(UUID restauranteId, UUID ventaId, TipoPago tipo, BigDecimal valor, String referencia) {
        this.restauranteId = restauranteId; this.ventaId = ventaId; this.tipo = tipo; this.valor = valor; this.referencia = referencia;
        this.status = tipo == TipoPago.TRANSFERENCIA ? PagoStatus.PENDING_VERIFICATION : PagoStatus.CONFIRMED;
        this.fechaHora = OffsetDateTime.now();
    }
    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getVentaId() { return ventaId; }
    public TipoPago getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public String getReferencia() { return referencia; }
    public PagoStatus getStatus() { return status; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public void verificar() { if (tipo != TipoPago.TRANSFERENCIA) throw new IllegalStateException("Solo se verifican transferencias"); status = PagoStatus.CONFIRMED; }
}
