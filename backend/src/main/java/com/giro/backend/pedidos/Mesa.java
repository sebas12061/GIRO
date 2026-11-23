package com.giro.backend.pedidos;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "mesas", uniqueConstraints = @UniqueConstraint(columnNames = {"restaurante_id", "numero"}))
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private Integer capacidad;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MesaEstado estado = MesaEstado.DISPONIBLE;

    protected Mesa() {
    }

    public Mesa(UUID restauranteId, Integer numero, Integer capacidad) {
        this.restauranteId = restauranteId;
        this.numero = numero;
        this.capacidad = capacidad;
    }

    public UUID getId() {
        return id;
    }

    public UUID getRestauranteId() {
        return restauranteId;
    }

    public Integer getNumero() {
        return numero;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public MesaEstado getEstado() {
        return estado;
    }

    public void ocupar() {
        estado = MesaEstado.OCUPADA;
    }

    public void liberar() {
        estado = MesaEstado.DISPONIBLE;
    }
}
