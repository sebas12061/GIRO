package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalles_pedido")
public class DetallePedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "producto_id", nullable = false)
    private UUID productoId;

    @Column(name = "opcion_menu_id")
    private UUID opcionMenuId;

    @Column(nullable = false, precision = 12, scale = 3)
    private BigDecimal cantidad;

    @Column(name = "precio_unitario", nullable = false, precision = 14, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    @Column(length = 500)
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private DetallePedidoEstado estado = DetallePedidoEstado.ACTIVO;

    protected DetallePedido() {
    }

    public DetallePedido(UUID productoId, UUID opcionMenuId, BigDecimal cantidad,
                         BigDecimal precioUnitario, String observaciones) {
        this.productoId = productoId;
        this.opcionMenuId = opcionMenuId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.observaciones = observaciones;
        this.subtotal = cantidad.multiply(precioUnitario);
    }

    void asignarPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public UUID getId() {
        return id;
    }

    public UUID getProductoId() {
        return productoId;
    }

    public UUID getOpcionMenuId() {
        return opcionMenuId;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public DetallePedidoEstado getEstado() {
        return estado;
    }
}
