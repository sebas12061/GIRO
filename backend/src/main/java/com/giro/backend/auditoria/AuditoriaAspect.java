package com.giro.backend.auditoria;

import java.time.OffsetDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.giro.backend.pedidos.PedidoAudit;
import com.giro.backend.pedidos.PedidoDto;

@Aspect
@Component
public class AuditoriaAspect {

    private final ApplicationEventPublisher publisher;

    public AuditoriaAspect(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @AfterReturning(pointcut = "@annotation(audit)", returning = "result")
    public void publish(JoinPoint joinPoint, PedidoAudit audit, Object result) {
        if (result instanceof PedidoDto pedido) {
            publisher.publishEvent(new AuditEvent(
                pedido.restauranteId(),
                null,
                audit.action(),
                "PEDIDO",
                pedido.id(),
                null,
                pedido.estado().name(),
                OffsetDateTime.now()
            ));
        }
    }
}
