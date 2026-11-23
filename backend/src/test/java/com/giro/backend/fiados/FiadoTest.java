package com.giro.backend.fiados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.Test;

class FiadoTest {

    @Test
    void cargoYAbonoCalculanSaldoYPaganFiado() {
        Fiado fiado = new Fiado(UUID.randomUUID(), UUID.randomUUID(), TipoFiado.UNICO,
            LocalDate.now(), LocalDate.now().plusDays(15));
        fiado.agregarMovimiento(new MovimientoFiado(UUID.randomUUID(), TipoMovimientoFiado.CARGO,
            new BigDecimal("50000.00"), "Venta fiada"));
        fiado.agregarMovimiento(new MovimientoFiado(null, TipoMovimientoFiado.ABONO,
            new BigDecimal("20000.00"), "Abono"));

        assertEquals(0, new BigDecimal("30000.00").compareTo(fiado.getSaldo()));

        fiado.agregarMovimiento(new MovimientoFiado(null, TipoMovimientoFiado.ABONO,
            new BigDecimal("30000.00"), "Cancelacion"));

        assertEquals(FiadoStatus.PAGADO, fiado.getStatus());
        assertEquals(BigDecimal.ZERO.setScale(2), fiado.getSaldo().setScale(2));
    }

    @Test
    void abonoNoPuedeSuperarSaldo() {
        Fiado fiado = new Fiado(UUID.randomUUID(), UUID.randomUUID(), TipoFiado.UNICO,
            LocalDate.now(), null);
        fiado.agregarMovimiento(new MovimientoFiado(null, TipoMovimientoFiado.CARGO,
            new BigDecimal("10000.00"), "Cargo"));

        assertThrows(IllegalArgumentException.class, () ->
            fiado.agregarMovimiento(new MovimientoFiado(null, TipoMovimientoFiado.ABONO,
                new BigDecimal("10001.00"), "Abono excesivo")));
    }
}
