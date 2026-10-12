package com.giro.backend.config;

import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.giro.backend.restaurant.Restaurant;
import com.giro.backend.restaurant.RestaurantRepository;
import com.giro.backend.seguridad.Permiso;
import com.giro.backend.seguridad.PermisoRepository;
import com.giro.backend.seguridad.Rol;
import com.giro.backend.seguridad.RolRepository;
import com.giro.backend.seguridad.Usuario;
import com.giro.backend.seguridad.UsuarioRepository;

@Configuration
@Profile("!test")
public class BootstrapDataConfig {

    @Bean
    CommandLineRunner bootstrapData(
        RestaurantRepository restaurantRepository,
        PermisoRepository permisoRepository,
        RolRepository rolRepository,
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        @Value("${giro.bootstrap.enabled:false}") boolean enabled,
        @Value("${giro.bootstrap.restaurant-identification:GIRO-LOCAL}") String restaurantIdentification,
        @Value("${giro.bootstrap.restaurant-name:Restaurante GIRO}") String restaurantName,
        @Value("${giro.bootstrap.admin-username:admin}") String adminUsername,
        @Value("${giro.bootstrap.admin-password:}") String adminPassword
    ) {
        return args -> {
            if (enabled) {
                initialize(restaurantRepository, permisoRepository, rolRepository, usuarioRepository,
                    passwordEncoder, restaurantIdentification, restaurantName, adminUsername, adminPassword);
            }
        };
    }

    @Transactional
    void initialize(
        RestaurantRepository restaurantRepository,
        PermisoRepository permisoRepository,
        RolRepository rolRepository,
        UsuarioRepository usuarioRepository,
        PasswordEncoder passwordEncoder,
        String restaurantIdentification,
        String restaurantName,
        String adminUsername,
        String adminPassword
    ) {
        if (adminPassword == null || adminPassword.isBlank()) {
            throw new IllegalStateException("Define giro.bootstrap.admin-password para activar el bootstrap");
        }

        Restaurant restaurant = restaurantRepository.findByIdentification(restaurantIdentification)
            .orElseGet(() -> restaurantRepository.save(new Restaurant(restaurantName, restaurantIdentification)));

        Permiso manageAll = permisoRepository.findByName("GIRO_ADMIN").orElseGet(
            () -> permisoRepository.save(new Permiso("GIRO_ADMIN")));
        Rol adminRole = rolRepository.findByName("ADMINISTRADOR").orElseGet(
            () -> rolRepository.save(new Rol("ADMINISTRADOR")));
        adminRole.getPermisos().add(manageAll);
        rolRepository.save(adminRole);

        if (usuarioRepository.findByUsername(adminUsername).isEmpty()) {
            Usuario admin = new Usuario(restaurant.getId(), adminUsername, passwordEncoder.encode(adminPassword));
            admin.getRoles().add(adminRole);
            usuarioRepository.save(admin);
        }
    }
}
