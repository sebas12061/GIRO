package com.giro.backend.pedidos;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
import jakarta.persistence.Version;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    private Long version;

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

    @Column(name = "mesa_id")
    private UUID mesaId;

    @Column(name = "cliente_id")
    private UUID clienteId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_servicio", nullable = false, length = 20)
    private TipoServicio tipoServicio = TipoServicio.MESA;

    @Column(length = 1000)
    private String observaciones;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<DetallePedido> detalles = new ArrayList<>();

    protected Pedido() {
    }

    public Pedido(UUID restauranteId, Long numero, BigDecimal total) {
        this.restauranteId = restauranteId;
        this.numero = numero;
        this.total = total;
    }

    public Pedido(UUID restauranteId, Long numero, UUID mesaId, UUID clienteId,
                  TipoServicio tipoServicio, String observaciones) {
        this.restauranteId = restauranteId;
        this.numero = numero;
        this.mesaId = mesaId;
        this.clienteId = clienteId;
        this.tipoServicio = tipoServicio;
        this.observaciones = observaciones;
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

    public UUID getMesaId() {
        return mesaId;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void reemplazarDetalles(List<DetallePedido> nuevosDetalles) {
        detalles.clear();
        nuevosDetalles.forEach(this::agregarDetalle);
        recalcularTotal();
    }

    public void agregarDetalle(DetallePedido detalle) {
        detalle.asignarPedido(this);
        detalles.add(detalle);
        recalcularTotal();
    }

    public void recalcularTotal() {
        total = detalles.stream()
            .map(DetallePedido::getSubtotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    void cambiarEstado(PedidoEstado nuevoEstado) {
        this.estado = nuevoEstado;
    }
}
