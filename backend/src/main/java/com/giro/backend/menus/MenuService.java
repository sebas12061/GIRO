package com.giro.backend.menus;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.productos.Categoria;
import com.giro.backend.productos.CategoriaRepository;
import com.giro.backend.productos.Producto;
import com.giro.backend.productos.ProductoRepository;
import com.giro.backend.productos.ProductoStatus;

@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public MenuService(MenuRepository menuRepository, ProductoRepository productoRepository,
                       CategoriaRepository categoriaRepository) {
        this.menuRepository = menuRepository;
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public MenuDto crear(CrearMenuRequest request) {
        if (request.fechaFin() != null && request.fechaInicio() != null
            && request.fechaFin().isBefore(request.fechaInicio())) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
        }

        Menu menu = new Menu(request.restauranteId(), request.name(),
            request.fechaInicio(), request.fechaFin());

        if (request.opciones() != null) {
            request.opciones().forEach(opcionRequest -> {
                if (opcionRequest.productoId() != null) {
                    Producto producto = productoRepository.findById(opcionRequest.productoId())
                        .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                        .filter(value -> value.getStatus() == ProductoStatus.ACTIVE)
                        .orElseThrow(() -> new IllegalArgumentException(
                            "Producto no encontrado, inactivo o ajeno al restaurante"));
                    menu.agregarOpcion(new OpcionMenu(producto.getId(), opcionRequest.categoriaId(),
                        opcionRequest.name(), opcionRequest.price(), opcionRequest.orden()));
                } else {
                    if (opcionRequest.categoriaId() != null) {
                        Categoria categoria = categoriaRepository.findById(opcionRequest.categoriaId())
                            .filter(value -> value.getRestauranteId().equals(request.restauranteId()))
                            .orElseThrow(() -> new IllegalArgumentException(
                                "Categoria no encontrada o ajena al restaurante"));
                        menu.agregarOpcion(new OpcionMenu(null, categoria.getId(), opcionRequest.name(),
                            opcionRequest.price(), opcionRequest.orden()));
                    } else {
                        menu.agregarOpcion(new OpcionMenu(null, null, opcionRequest.name(),
                            opcionRequest.price(), opcionRequest.orden()));
                    }
                }
            });
        }

        return toDto(menuRepository.save(menu));
    }

    @Transactional(readOnly = true)
    public List<MenuDto> listarActivos(UUID restauranteId) {
        return menuRepository.findAllByRestauranteIdAndStatusOrderByName(restauranteId, MenuStatus.ACTIVE)
            .stream()
            .map(this::toDto)
            .toList();
    }

    public MenuDto desactivar(UUID restauranteId, UUID menuId) {
        Menu menu = menuRepository.findByIdAndRestauranteId(menuId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Menu no encontrado para el restaurante"));
        menu.desactivar();
        return toDto(menuRepository.save(menu));
    }

    private MenuDto toDto(Menu menu) {
        List<OpcionMenuDto> opciones = menu.getOpciones().stream()
            .map(opcion -> new OpcionMenuDto(opcion.getId(), opcion.getProductoId(), opcion.getCategoriaId(),
                opcion.getName(), opcion.getPrice(), opcion.getOrden(), opcion.getStatus()))
            .toList();
        return new MenuDto(menu.getId(), menu.getRestauranteId(), menu.getName(), menu.getFechaInicio(),
            menu.getFechaFin(), menu.getStatus(), opciones);
    }
}
