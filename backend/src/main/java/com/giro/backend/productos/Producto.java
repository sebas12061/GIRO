package com.giro.backend.productos;

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
@Table(name = "productos")
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "restaurante_id", nullable = false) private UUID restauranteId;
    @Column(nullable = false, length = 150) private String name;
    @Column(nullable = false, precision = 14, scale = 2) private BigDecimal price;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "categoria_id") private Categoria categoria;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "unidad_id") private UnidadOperativa unidad;
    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) private ProductoStatus status = ProductoStatus.ACTIVE;
    protected Producto() { }
    public Producto(UUID restauranteId, String name, BigDecimal price, Categoria categoria, UnidadOperativa unidad) {
        this.restauranteId = restauranteId; this.name = name; this.price = price; this.categoria = categoria; this.unidad = unidad;
    }
    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Categoria getCategoria() { return categoria; }
    public UnidadOperativa getUnidad() { return unidad; }
    public ProductoStatus getStatus() { return status; }
}
