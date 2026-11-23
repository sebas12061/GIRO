package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.time.LocalDate;
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
@Table(name = "movimientos_producto")
public class MovimientoProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Column(name = "unidad_id", nullable = false)
    private UUID unidadId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimientoProducto tipoMovimiento;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidad;

    @Column(length = 120)
    private String referencia;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(length = 500)
    private String observacion;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    protected MovimientoProducto() {
    }

    public MovimientoProducto(UUID restauranteId, UUID productoId, UUID unidadId, LocalDate fecha,
                              TipoMovimientoProducto tipoMovimiento, BigDecimal cantidad,
                              String referencia, UUID usuarioId, String observacion) {
        this.restauranteId = restauranteId;
        this.productoId = productoId;
        this.unidadId = unidadId;
        this.fecha = fecha;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.usuarioId = usuarioId;
        this.observacion = observacion;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getProductoId() { return productoId; }
    public UUID getUnidadId() { return unidadId; }
    public LocalDate getFecha() { return fecha; }
    public TipoMovimientoProducto getTipoMovimiento() { return tipoMovimiento; }
    public BigDecimal getCantidad() { return cantidad; }
    public String getReferencia() { return referencia; }
    public UUID getUsuarioId() { return usuarioId; }
    public String getObservacion() { return observacion; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
}
