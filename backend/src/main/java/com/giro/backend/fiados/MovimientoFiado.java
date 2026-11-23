package com.giro.backend.fiados;

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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimientos_fiado")
public class MovimientoFiado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fiado_id")
    private Fiado fiado;

    @Column(name = "venta_id")
    private UUID ventaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimientoFiado tipoMovimiento;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    @Column(length = 500)
    private String observacion;

    protected MovimientoFiado() {
    }

    public MovimientoFiado(UUID ventaId, TipoMovimientoFiado tipoMovimiento,
                           BigDecimal valor, String observacion) {
        this.ventaId = ventaId;
        this.tipoMovimiento = tipoMovimiento;
        this.valor = valor;
        this.observacion = observacion;
    }

    void asignarFiado(Fiado fiado) {
        this.fiado = fiado;
    }

    public UUID getId() { return id; }
    public UUID getVentaId() { return ventaId; }
    public TipoMovimientoFiado getTipoMovimiento() { return tipoMovimiento; }
    public BigDecimal getValor() { return valor; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public String getObservacion() { return observacion; }
}
