package com.giro.backend.clientes;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public ClienteDto crear(CrearClienteRequest request) {
        Cliente cliente = repository.save(new Cliente(request.restauranteId(), request.name(),
            request.telefono(), request.direccionReferencia(), request.tipoCliente()));
        return toDto(cliente);
    }

    @Transactional(readOnly = true)
    public List<ClienteDto> listarActivos(UUID restauranteId) {
        return repository.findAllByRestauranteIdAndStatusOrderByName(restauranteId, ClienteStatus.ACTIVE)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public ClienteDto desactivar(UUID restauranteId, UUID clienteId) {
        Cliente cliente = repository.findByIdAndRestauranteId(clienteId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado para el restaurante"));
        cliente.desactivar();
        return toDto(repository.save(cliente));
    }

    private ClienteDto toDto(Cliente cliente) {
        return new ClienteDto(cliente.getId(), cliente.getRestauranteId(), cliente.getName(),
            cliente.getTelefono(), cliente.getDireccionReferencia(), cliente.getTipoCliente(), cliente.getStatus());
    }
}
