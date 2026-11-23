package com.giro.backend.ventas;

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
import jakarta.persistence.Version;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(name = "pedido_id", nullable = false, unique = true)
    private UUID pedidoId;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private VentaStatus status = VentaStatus.PENDIENTE;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    protected Venta() {
    }

    public Venta(UUID restauranteId, UUID pedidoId, BigDecimal subtotal, BigDecimal total) {
        this.restauranteId = restauranteId;
        this.pedidoId = pedidoId;
        this.subtotal = subtotal;
        this.total = total;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getPedidoId() { return pedidoId; }
    public BigDecimal getSubtotal() { return subtotal; }
    public BigDecimal getTotal() { return total; }
    public VentaStatus getStatus() { return status; }
    public OffsetDateTime getFechaHora() { return fechaHora; }

    public void pagar() {
        status = VentaStatus.PAGADA;
    }
}
