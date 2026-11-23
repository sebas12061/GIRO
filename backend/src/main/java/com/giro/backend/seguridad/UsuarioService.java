package com.giro.backend.seguridad;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                          PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @SeguridadAudit(action = "CREAR_USUARIO")
    public UsuarioDto crear(CrearUsuarioRequest request) {
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese username");
        }

        Set<Rol> roles = cargarRoles(request.roleIds());
        Usuario usuario = request.empleadoId() == null
            ? new Usuario(request.restauranteId(), request.username(), passwordEncoder.encode(request.password()))
            : new Usuario(request.restauranteId(), request.empleadoId(), request.username(),
                passwordEncoder.encode(request.password()));
        usuario.getRoles().clear();
        usuario.getRoles().addAll(roles);
        return toDto(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDto> listar(UUID restauranteId) {
        return usuarioRepository.findAllByRestauranteIdOrderByUsername(restauranteId)
            .stream()
            .map(this::toDto)
            .toList();
    }

    @SeguridadAudit(action = "ACTIVAR_USUARIO")
    public UsuarioDto activar(UUID restauranteId, UUID usuarioId) {
        Usuario usuario = buscarUsuario(restauranteId, usuarioId);
        usuario.activar();
        return toDto(usuarioRepository.save(usuario));
    }

    @SeguridadAudit(action = "DESACTIVAR_USUARIO")
    public UsuarioDto desactivar(UUID restauranteId, UUID usuarioId) {
        Usuario usuario = buscarUsuario(restauranteId, usuarioId);
        usuario.desactivar();
        return toDto(usuarioRepository.save(usuario));
    }

    @SeguridadAudit(action = "CAMBIAR_PASSWORD_USUARIO")
    public UsuarioDto cambiarPassword(UUID restauranteId, UUID usuarioId, CambiarPasswordRequest request) {
        Usuario usuario = buscarUsuario(restauranteId, usuarioId);
        usuario.cambiarPassword(passwordEncoder.encode(request.password()));
        return toDto(usuarioRepository.save(usuario));
    }

    @SeguridadAudit(action = "ASIGNAR_ROLES_USUARIO")
    public UsuarioDto asignarRoles(UUID restauranteId, UUID usuarioId, AsignarRolesRequest request) {
        Usuario usuario = buscarUsuario(restauranteId, usuarioId);
        Set<Rol> roles = cargarRoles(request.roleIds());
        usuario.getRoles().clear();
        usuario.getRoles().addAll(roles);
        return toDto(usuarioRepository.save(usuario));
    }

    private Usuario buscarUsuario(UUID restauranteId, UUID usuarioId) {
        return usuarioRepository.findByIdAndRestauranteId(usuarioId, restauranteId)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado para el restaurante"));
    }

    private Set<Rol> cargarRoles(Set<UUID> roleIds) {
        Set<Rol> roles = new HashSet<>();
        roleIds.forEach(roleId -> {
            Rol rol = rolRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + roleId));
            roles.add(rol);
        });
        return roles;
    }

    private UsuarioDto toDto(Usuario usuario) {
        Set<String> roles = usuario.getRoles().stream()
            .map(Rol::getName)
            .collect(Collectors.toSet());
        return new UsuarioDto(usuario.getId(), usuario.getRestauranteId(), usuario.getEmpleadoId(),
            usuario.getUsername(), usuario.isActivo(), roles);
    }
}
