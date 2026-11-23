package com.giro.backend.seguridad;

import java.util.Set;
import java.util.UUID;

public record RolDto(UUID id, String name, Set<String> permisos) {
}
