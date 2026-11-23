package com.giro.backend.inventario;

import java.time.LocalDate;
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
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "cierres_operativos", uniqueConstraints =
    @UniqueConstraint(columnNames = {"restaurante_id", "fecha"}))
public class CierreOperativo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CierreOperativoStatus status = CierreOperativoStatus.OPEN;

    @OneToMany(mappedBy = "cierre", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<DetalleCierreOperativo> detalles = new ArrayList<>();

    protected CierreOperativo() {
    }

    public CierreOperativo(UUID restauranteId, LocalDate fecha, UUID usuarioId) {
        this.restauranteId = restauranteId;
        this.fecha = fecha;
        this.usuarioId = usuarioId;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public LocalDate getFecha() { return fecha; }
    public UUID getUsuarioId() { return usuarioId; }
    public CierreOperativoStatus getStatus() { return status; }
    public List<DetalleCierreOperativo> getDetalles() { return detalles; }

    public void agregarDetalle(DetalleCierreOperativo detalle) {
        if (status == CierreOperativoStatus.CLOSED) {
            throw new IllegalStateException("No se pueden modificar detalles de un cierre cerrado");
        }
        detalle.asignarCierre(this);
        detalles.add(detalle);
    }

    public void cerrar() {
        if (status == CierreOperativoStatus.CLOSED) {
            throw new IllegalStateException("El cierre operativo ya esta cerrado");
        }
        status = CierreOperativoStatus.CLOSED;
    }
}
