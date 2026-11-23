package com.giro.backend.seguridad;

import java.time.OffsetDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.giro.backend.auditoria.AuditEvent;

@Aspect
@Component
public class SeguridadAuditoriaAspect {

    private final ApplicationEventPublisher publisher;

    public SeguridadAuditoriaAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @AfterReturning(pointcut = "@annotation(audit)", returning = "result")
    public void publish(JoinPoint joinPoint, SeguridadAudit audit, Object result) {
        if (result instanceof UsuarioDto usuario) {
            publisher.publishEvent(new AuditEvent(
                usuario.restauranteId(),
                null,
                audit.action(),
                "USUARIO",
                usuario.id(),
                null,
                "activo=" + usuario.activo() + "; roles=" + usuario.roles(),
                OffsetDateTime.now()
            ));
        } else if (result instanceof RolDto rol) {
            publisher.publishEvent(new AuditEvent(
                null,
                null,
                audit.action(),
                "ROL",
                rol.id(),
                null,
                "permisos=" + rol.permisos(),
                OffsetDateTime.now()
            ));
        }
    }
}
