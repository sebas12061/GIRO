package com.giro.backend.auditoria;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.aspectj.lang.JoinPoint;
import org.mockito.Mockito;

import com.giro.backend.pedidos.PedidoAudit;
import com.giro.backend.pedidos.PedidoDto;
import com.giro.backend.pedidos.PedidoEstado;

class AuditoriaAspectTest {

    @Test
    void publicaEventoDeAuditoria() {
        ApplicationEventPublisher publisher = mock(ApplicationEventPublisher.class);
        AuditoriaAspect aspect = new AuditoriaAspect(publisher);
        UUID restaurantId = UUID.randomUUID();
        UUID pedidoId = UUID.randomUUID();
        PedidoDto pedido = new PedidoDto(pedidoId, restaurantId, 1L, PedidoEstado.CONFIRMADO,
            BigDecimal.TEN, OffsetDateTime.now());
        JoinPoint joinPoint = Mockito.mock(JoinPoint.class);
        PedidoAudit audit = Mockito.mock(PedidoAudit.class);
        Mockito.when(audit.action()).thenReturn("CAMBIAR_ESTADO_PEDIDO");

        aspect.publish(joinPoint, audit, pedido);

        verify(publisher).publishEvent(org.mockito.ArgumentMatchers.any(AuditEvent.class));
    }
}
