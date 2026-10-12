package com.giro.backend.productos;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categorias")
public class Categoria {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @Column(name = "restaurante_id", nullable = false) private UUID restauranteId;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false) private boolean activo = true;
    protected Categoria() { }
    public Categoria(UUID restauranteId, String name) { this.restauranteId = restauranteId; this.name = name; }
    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public boolean isActivo() { return activo; }
}
