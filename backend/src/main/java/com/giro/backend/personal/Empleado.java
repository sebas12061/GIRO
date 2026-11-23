package com.giro.backend.personal;

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
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 50)
    private String documento;

    @Column(length = 30)
    private String telefono;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EmpleadoStatus status = EmpleadoStatus.ACTIVE;

    protected Empleado() {
    }

    public Empleado(UUID restauranteId, String name, String documento, String telefono) {
        this.restauranteId = restauranteId;
        this.name = name;
        this.documento = documento;
        this.telefono = telefono;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public String getDocumento() { return documento; }
    public String getTelefono() { return telefono; }
    public EmpleadoStatus getStatus() { return status; }

    public void desactivar() {
        status = EmpleadoStatus.INACTIVE;
    }
}
