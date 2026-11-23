package com.giro.backend.caja;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
@Table(name = "cuadres")
public class Cuadre {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "caja_id", nullable = false, unique = true)
    private UUID cajaId;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal base;

    @Column(name = "ventas_efectivo", nullable = false, precision = 14, scale = 2)
    private BigDecimal ventasEfectivo;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal transferencias;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal fiados;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal gastos;

    @Column(name = "efectivo_esperado", nullable = false, precision = 14, scale = 2)
    private BigDecimal efectivoEsperado;

    @Column(name = "efectivo_real", nullable = false, precision = 14, scale = 2)
    private BigDecimal efectivoReal;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal diferencia;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(nullable = false)
    private OffsetDateTime fechaHora = OffsetDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CuadreStatus status = CuadreStatus.OPEN;

    protected Cuadre() {
    }

    public Cuadre(UUID cajaId, BigDecimal base, BigDecimal ventasEfectivo,
                  BigDecimal transferencias, BigDecimal fiados, BigDecimal gastos,
                  BigDecimal efectivoEsperado, BigDecimal efectivoReal,
                  UUID usuarioId) {
        this.cajaId = cajaId;
        this.base = base;
        this.ventasEfectivo = ventasEfectivo;
        this.transferencias = transferencias;
        this.fiados = fiados;
        this.gastos = gastos;
        this.efectivoEsperado = efectivoEsperado;
        this.efectivoReal = efectivoReal;
        this.diferencia = efectivoReal.subtract(efectivoEsperado);
        this.usuarioId = usuarioId;
    }

    public UUID getId() { return id; }
    public UUID getCajaId() { return cajaId; }
    public BigDecimal getBase() { return base; }
    public BigDecimal getVentasEfectivo() { return ventasEfectivo; }
    public BigDecimal getTransferencias() { return transferencias; }
    public BigDecimal getFiados() { return fiados; }
    public BigDecimal getGastos() { return gastos; }
    public BigDecimal getEfectivoEsperado() { return efectivoEsperado; }
    public BigDecimal getEfectivoReal() { return efectivoReal; }
    public BigDecimal getDiferencia() { return diferencia; }
    public UUID getUsuarioId() { return usuarioId; }
    public OffsetDateTime getFechaHora() { return fechaHora; }
    public CuadreStatus getStatus() { return status; }

    public void cerrar() {
        if (status == CuadreStatus.CLOSED) {
            throw new IllegalStateException("El cuadre ya esta cerrado");
        }
        status = CuadreStatus.CLOSED;
    }

    public void modificarEfectivoReal(BigDecimal nuevoEfectivoReal) {
        if (status != CuadreStatus.CLOSED) {
            throw new IllegalStateException("Solo se modifica un cuadre cerrado administrativamente");
        }
        efectivoReal = nuevoEfectivoReal;
        diferencia = efectivoReal.subtract(efectivoEsperado);
        status = CuadreStatus.CLOSED;
    }
}
