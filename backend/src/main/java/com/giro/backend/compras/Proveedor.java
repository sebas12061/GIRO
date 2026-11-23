package com.giro.backend.compras;

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
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String identificacion;

    @Column(length = 30)
    private String telefono;

    @Column(length = 255)
    private String direccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ProveedorStatus status = ProveedorStatus.ACTIVE;

    protected Proveedor() {
    }

    public Proveedor(UUID restauranteId, String name, String identificacion, String telefono, String direccion) {
        this.restauranteId = restauranteId;
        this.name = name;
        this.identificacion = identificacion;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public String getIdentificacion() { return identificacion; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public ProveedorStatus getStatus() { return status; }

    public void desactivar() {
        status = ProveedorStatus.INACTIVE;
    }
}
