package com.giro.backend.pagos;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class DefaultCashExpectedCalculator implements CashExpectedCalculator {

    @Override
    public BigDecimal calculate(BigDecimal base, BigDecimal cashIncome, BigDecimal expenses) {
        return value(base).add(value(cashIncome)).subtract(value(expenses));
    }

    private BigDecimal value(BigDecimal amount) {
        return amount == null ? BigDecimal.ZERO : amount;
    }
}
