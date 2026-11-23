package com.giro.backend.fiados;

import java.math.BigDecimal;
import java.time.LocalDate;
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

@Entity
@Table(name = "fiados")
public class Fiado {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoFiado tipo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FiadoStatus status = FiadoStatus.ABIERTO;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    @OneToMany(mappedBy = "fiado", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fechaHora ASC")
    private List<MovimientoFiado> movimientos = new ArrayList<>();

    protected Fiado() {
    }

    public Fiado(UUID restauranteId, UUID clienteId, TipoFiado tipo,
                 LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.restauranteId = restauranteId;
        this.clienteId = clienteId;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getClienteId() { return clienteId; }
    public TipoFiado getTipo() { return tipo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public BigDecimal getSaldo() { return saldo; }
    public FiadoStatus getStatus() { return status; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public List<MovimientoFiado> getMovimientos() { return movimientos; }

    public void agregarMovimiento(MovimientoFiado movimiento) {
        if (status == FiadoStatus.PAGADO) {
            throw new IllegalStateException("No se pueden registrar movimientos en un fiado pagado");
        }
        movimiento.asignarFiado(this);
        movimientos.add(movimiento);
        if (movimiento.getTipoMovimiento() == TipoMovimientoFiado.CARGO) {
            saldo = saldo.add(movimiento.getValor());
        } else {
            if (movimiento.getValor().compareTo(saldo) > 0) {
                throw new IllegalArgumentException("El abono no puede superar el saldo del fiado");
            }
            saldo = saldo.subtract(movimiento.getValor());
            if (saldo.compareTo(BigDecimal.ZERO) == 0) {
                status = FiadoStatus.PAGADO;
            }
        }
    }
}
