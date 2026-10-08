package com.giro.backend.auditoria;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuditoriaEventListener {

    private final AuditoriaRepository repository;

    public AuditoriaEventListener(AuditoriaRepository repository) {
        this.repository = repository;
    }

    @EventListener
    @Transactional
    public void onAuditEvent(AuditEvent event) {
        repository.save(new Auditoria(event));
    }
}
