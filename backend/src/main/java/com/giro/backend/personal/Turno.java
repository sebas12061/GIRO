package com.giro.backend.personal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "turnos")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "restaurante_id", nullable = false)
    private UUID restauranteId;

    @Column(name = "empleado_id", nullable = false)
    private UUID empleadoId;

    @Column(nullable = false)
    private LocalDate fecha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTurno tipo;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TurnoStatus status = TurnoStatus.PROGRAMADO;

    protected Turno() {
    }

    public Turno(UUID restauranteId, UUID empleadoId, LocalDate fecha, TipoTurno tipo,
                 LocalTime horaInicio, LocalTime horaFin, BigDecimal valor) {
        this.restauranteId = restauranteId;
        this.empleadoId = empleadoId;
        this.fecha = fecha;
        this.tipo = tipo;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.valor = valor;
    }

    public UUID getId() { return id; }
    public UUID getRestauranteId() { return restauranteId; }
    public UUID getEmpleadoId() { return empleadoId; }
    public LocalDate getFecha() { return fecha; }
    public TipoTurno getTipo() { return tipo; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public BigDecimal getValor() { return valor; }
    public TurnoStatus getStatus() { return status; }

    public void iniciar() {
        if (status != TurnoStatus.PROGRAMADO) {
            throw new IllegalStateException("Solo se inicia un turno programado");
        }
        status = TurnoStatus.INICIADO;
    }

    public void finalizar() {
        if (status != TurnoStatus.INICIADO) {
            throw new IllegalStateException("Solo se finaliza un turno iniciado");
        }
        status = TurnoStatus.FINALIZADO;
    }

    public void cancelar() {
        if (status == TurnoStatus.FINALIZADO) {
            throw new IllegalStateException("No se puede cancelar un turno finalizado");
        }
        status = TurnoStatus.CANCELADO;
    }
}
