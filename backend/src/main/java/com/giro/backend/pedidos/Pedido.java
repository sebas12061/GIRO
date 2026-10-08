package com.giro.backend.pedidos;

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
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false)
    private Long numero;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PedidoEstado estado = PedidoEstado.CREADO;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total = BigDecimal.ZERO;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    protected Pedido() {
    }

    public Pedido(UUID restauranteId, Long numero, BigDecimal total) {
        this.restauranteId = restauranteId;
        this.numero = numero;
        this.total = total;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public Long getNumero() {
        return numero;
    }

    public PedidoEstado getEstado() {
        return estado;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OffsetDateTime getFechaHora() {
        return fechaHora;
    }

    void cambiarEstado(PedidoEstado nuevoEstado) {
        this.estado = nuevoEstado;
    }
}
