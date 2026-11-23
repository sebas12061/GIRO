package com.giro.backend.pedidos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MesaService {

    private final MesaRepository repository;

    public MesaService(MesaRepository repository) {
        this.repository = repository;
    }

    public MesaDto crear(CrearMesaRequest request) {
        if (repository.existsByRestauranteIdAndNumero(request.restauranteId(), request.numero())) {
            throw new IllegalArgumentException("Ya existe una mesa con ese numero en el restaurante");
        }
        Mesa mesa = repository.save(new Mesa(request.restauranteId(), request.numero(), request.capacidad()));
        return toDto(mesa);
    }

    @Transactional(readOnly = true)
    public List<MesaDto> listar(UUID restauranteId) {
        return repository.findAllByRestauranteIdOrderByNumero(restauranteId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    private MesaDto toDto(Mesa mesa) {
        return new MesaDto(mesa.getId(), mesa.getRestauranteId(), mesa.getNumero(), mesa.getCapacidad(), mesa.getEstado());
    }
}
