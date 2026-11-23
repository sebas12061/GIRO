package com.giro.backend.inventario;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_cierre_operativo")
public class DetalleCierreOperativo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cierre_id")
    private CierreOperativo cierre;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Column(name = "cantidad_queda", nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidadQueda;

    @Column(name = "unidad_id", nullable = false)
    private UUID unidadId;

    @Column(length = 500)
    private String observacion;

    protected DetalleCierreOperativo() {
    }

    public DetalleCierreOperativo(UUID productoId, BigDecimal cantidadQueda,
                                  UUID unidadId, String observacion) {
        this.productoId = productoId;
        this.cantidadQueda = cantidadQueda;
        this.unidadId = unidadId;
        this.observacion = observacion;
    }

    void asignarCierre(CierreOperativo cierre) {
        this.cierre = cierre;
    }

    public UUID getId() { return id; }
    public UUID getProductoId() { return productoId; }
    public BigDecimal getCantidadQueda() { return cantidadQueda; }
    public UUID getUnidadId() { return unidadId; }
    public String getObservacion() { return observacion; }
}
