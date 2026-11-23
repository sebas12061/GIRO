package com.giro.backend.compras;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;

@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(name = "proveedor_id", nullable = false)
    private UUID proveedorId;

    @Column(name = "caja_id")
    private UUID cajaId;

    @Column(nullable = false)
    private OffsetDateTime fecha = OffsetDateTime.now();

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CompraStatus status = CompraStatus.REGISTRADA;

    @Column(length = 1000)
    private String observacion;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<DetalleCompra> detalles = new ArrayList<>();

    protected Compra() {
    }

    public Compra(UUID restauranteId, UUID proveedorId, UUID cajaId, String observacion) {
        this.restauranteId = restauranteId;
        this.proveedorId = proveedorId;
        this.cajaId = cajaId;
        this.observacion = observacion;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getProveedorId() { return proveedorId; }
    public UUID getCajaId() { return cajaId; }
    public OffsetDateTime getFecha() { return fecha; }
    public BigDecimal getTotal() { return total; }
    public CompraStatus getStatus() { return status; }
    public String getObservacion() { return observacion; }
    public List<DetalleCompra> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleCompra detalle) {
        detalle.asignarCompra(this);
        detalles.add(detalle);
        recalcularTotal();
    }

    private void recalcularTotal() {
        total = detalles.stream()
            .map(DetalleCompra::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void anular() {
        if (status == CompraStatus.ANULADA) {
            throw new IllegalStateException("La compra ya esta anulada");
        }
        status = CompraStatus.ANULADA;
    }
}
