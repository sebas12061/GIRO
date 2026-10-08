package com.giro.backend.pagos;

import java.math.BigDecimal;

public interface CashExpectedCalculator {

    BigDecimal calculate(BigDecimal base, BigDecimal cashIncome, BigDecimal expenses);
}
