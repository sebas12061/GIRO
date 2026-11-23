package com.giro.backend.compras;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProveedorService {

    private final ProveedorRepository repository;

    public ProveedorService(ProveedorRepository repository) {
        this.repository = repository;
    }

    public ProveedorDto crear(CrearProveedorRequest request) {
        Proveedor proveedor = repository.save(new Proveedor(
            request.restauranteId(), request.name(), request.identificacion(),
            request.telefono(), request.direccion()));
        return toDto(proveedor);
    }

    @Transactional(readOnly = true)
    public List<ProveedorDto> listarActivos(UUID restauranteId) {
        return repository.findAllByRestauranteIdAndStatusOrderByName(restauranteId, ProveedorStatus.ACTIVE)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public ProveedorDto desactivar(UUID restauranteId, UUID proveedorId) {
        Proveedor proveedor = repository.findByIdAndRestauranteId(proveedorId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Proveedor no encontrado para el restaurante"));
        proveedor.desactivar();
        return toDto(repository.save(proveedor));
    }

    private ProveedorDto toDto(Proveedor proveedor) {
        return new ProveedorDto(proveedor.getId(), proveedor.getRestauranteId(), proveedor.getName(),
            proveedor.getIdentificacion(), proveedor.getTelefono(), proveedor.getDireccion(), proveedor.getStatus());
    }
}
