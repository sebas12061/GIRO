package com.giro.backend.clientes;

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
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 30)
    private String telefono;

    @Column(name = "direccion_referencia", length = 255)
    private String direccionReferencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_cliente", nullable = false, length = 20)
    private TipoCliente tipoCliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ClienteStatus status = ClienteStatus.ACTIVE;

    protected Cliente() {
    }

    public Cliente(UUID restauranteId, String name, String telefono,
                   String direccionReferencia, TipoCliente tipoCliente) {
        this.restauranteId = restauranteId;
        this.name = name;
        this.telefono = telefono;
        this.direccionReferencia = direccionReferencia;
        this.tipoCliente = tipoCliente;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public String getTelefono() { return telefono; }
    public String getDireccionReferencia() { return direccionReferencia; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public ClienteStatus getStatus() { return status; }

    public void desactivar() {
        status = ClienteStatus.INACTIVE;
    }
}
