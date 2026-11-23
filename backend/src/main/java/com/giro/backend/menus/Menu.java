package com.giro.backend.menus;

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

@Entity
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orden ASC")
    private List<OpcionMenu> opciones = new ArrayList<>();

    protected Menu() {
    }

    public Menu(UUID restauranteId, String name, LocalDate fechaInicio, LocalDate fechaFin) {
        this.restauranteId = restauranteId;
        this.name = name;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public String getName() { return name; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public MenuStatus getStatus() { return status; }
    public List<OpcionMenu> getOpciones() { return opciones; }

    public void agregarOpcion(OpcionMenu opcion) {
        opcion.asignarMenu(this);
        opciones.add(opcion);
    }

    public void desactivar() {
        status = MenuStatus.INACTIVE;
    }
}
