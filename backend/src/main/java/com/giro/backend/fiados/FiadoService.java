package com.giro.backend.fiados;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.clientes.Cliente;
import com.giro.backend.clientes.ClienteRepository;
import com.giro.backend.clientes.ClienteStatus;
import com.giro.backend.ventas.VentaRepository;

@Service
@Transactional
public class FiadoService {

    private final FiadoRepository fiadoRepository;
    private final ClienteRepository clienteRepository;
    private final VentaRepository ventaRepository;

    public FiadoService(FiadoRepository fiadoRepository, ClienteRepository clienteRepository,
                        VentaRepository ventaRepository) {
        this.fiadoRepository = fiadoRepository;
        this.clienteRepository = clienteRepository;
        this.ventaRepository = ventaRepository;
    }

    public FiadoDto crear(CrearFiadoRequest request) {
        Cliente cliente = clienteRepository.findByIdAndRestauranteId(request.clienteId(), request.restauranteId())
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado para el restaurante"));
        if (cliente.getStatus() != ClienteStatus.ACTIVE) {
            throw new IllegalStateException("No se puede fiar a un cliente inactivo");
        }

        if (request.ventaId() != null) {
            ventaRepository.findById(request.ventaId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada para el restaurante"));
        }

        Fiado fiado = new Fiado(request.restauranteId(), cliente.getId(), request.tipo(),
            LocalDate.now(), request.fechaVencimiento());
        return toDto(fiadoRepository.save(fiado));
    }

    public FiadoDto registrarMovimiento(RegistrarMovimientoFiadoRequest request) {
        Fiado fiado = fiadoRepository.findByIdAndRestauranteId(request.fiadoId(), request.restauranteId())
            .orElseThrow(() -> new IllegalArgumentException("Fiado no encontrado para el restaurante"));

        if (request.ventaId() != null) {
            ventaRepository.findById(request.ventaId())
                .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada para el restaurante"));
        }

        fiado.agregarMovimiento(new MovimientoFiado(request.ventaId(), request.tipoMovimiento(),
            request.valor(), request.observacion()));
        return toDto(fiadoRepository.save(fiado));
    }

    @Transactional(readOnly = true)
    public List<FiadoDto> listar(UUID restauranteId) {
        return fiadoRepository.findAllByRestauranteIdOrderByFechaInicioDesc(restauranteId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public FiadoDto consultar(UUID restauranteId, UUID fiadoId) {
        Fiado fiado = fiadoRepository.findByIdAndRestauranteId(fiadoId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Fiado no encontrado para el restaurante"));
        return toDto(fiado);
    }

    private FiadoDto toDto(Fiado fiado) {
        List<MovimientoFiadoDto> movimientos = fiado.getMovimientos().stream()
            .map(movimiento -> new MovimientoFiadoDto(movimiento.getId(), movimiento.getVentaId(),
                movimiento.getTipoMovimiento(), movimiento.getValor(), movimiento.getFechaHora(),
                movimiento.getObservacion()))
            .toList();
        return new FiadoDto(fiado.getId(), fiado.getRestauranteId(), fiado.getClienteId(), fiado.getTipo(),
            fiado.getFechaInicio(), fiado.getFechaVencimiento(), fiado.getSaldo(), fiado.getStatus(),
            fiado.getFechaHora(), movimientos);
    }
}
