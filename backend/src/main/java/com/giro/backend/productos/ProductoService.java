package com.giro.backend.productos;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductoService {
    private final ProductoRepository repository;
    private final ProductoMapper mapper;
    public ProductoService(ProductoRepository repository, ProductoMapper mapper) { this.repository = repository; this.mapper = mapper; }
    @Transactional(readOnly = true)
    public List<ProductoDto> listarActivos(UUID restauranteId) {
        return repository.findAllByRestauranteIdAndStatusOrderByName(restauranteId, ProductoStatus.ACTIVE).stream().map(mapper::toDto).toList();
    }
}
