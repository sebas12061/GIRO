package com.giro.backend.menus;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/menus")
public class MenuController {

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MenuDto crear(@Valid @RequestBody CrearMenuRequest request) {
        return service.crear(request);
    }

    @GetMapping
    public List<MenuDto> listar(@RequestParam UUID restauranteId) {
        return service.listarActivos(restauranteId);
    }

    @PutMapping("/{menuId}/desactivar")
    public MenuDto desactivar(@PathVariable UUID menuId, @RequestParam UUID restauranteId) {
        return service.desactivar(restauranteId, menuId);
    }
}
