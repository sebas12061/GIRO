package com.giro.backend.caja;

import java.time.OffsetDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.giro.backend.auditoria.AuditEvent;

@Aspect
@Component
public class CajaAuditoriaAspect {

    private final ApplicationEventPublisher publisher;

    public CajaAuditoriaAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @AfterReturning(pointcut = "@annotation(audit)", returning = "result")
    public void publish(JoinPoint joinPoint, CajaAudit audit, Object result) {
        if (result instanceof CuadreDto cuadre) {
            publisher.publishEvent(new AuditEvent(
                null,
                cuadre.usuarioId(),
                audit.action(),
                "CUADRE",
                cuadre.id(),
                null,
                "efectivoReal=" + cuadre.efectivoReal(),
                OffsetDateTime.now()
            ));
        }
    }
}
