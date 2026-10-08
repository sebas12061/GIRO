package com.giro.backend.auditoria;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditoriaRepository extends JpaRepository<Auditoria, UUID> {
}
