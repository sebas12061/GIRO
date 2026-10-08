package com.giro.backend.seguridad;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, UsuarioRepository usuarioRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        Usuario usuario = usuarioRepository.findByUsername(request.username())
            .orElseThrow(() -> new IllegalArgumentException("Credenciales invalidas"));
        return jwtService.generateToken(usuario);
    }
}
