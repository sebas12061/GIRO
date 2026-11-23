package com.giro.backend.menus;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "opciones_menu")
public class OpcionMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @Column(name = "producto_id")
    private UUID productoId;

    @Column(name = "categoria_id")
    private UUID categoriaId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private Integer orden;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MenuStatus status = MenuStatus.ACTIVE;

    protected OpcionMenu() {
    }

    public OpcionMenu(UUID productoId, UUID categoriaId, String name, BigDecimal price, Integer orden) {
        this.productoId = productoId;
        this.categoriaId = categoriaId;
        this.name = name;
        this.price = price;
        this.orden = orden;
    }

    void asignarMenu(Menu menu) {
        this.menu = menu;
    }

    public UUID getId() { return id; }
    public UUID getProductoId() { return productoId; }
    public UUID getCategoriaId() { return categoriaId; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getOrden() { return orden; }
    public MenuStatus getStatus() { return status; }

    public void desactivar() {
        status = MenuStatus.INACTIVE;
    }
}
