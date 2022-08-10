package com.xm.accounting.dto;

import com.xm.accounting.entity.Currency;

import java.math.BigDecimal;

public class MoneyDTO {

    private BigDecimal value;
    private Currency currency;

    public BigDecimal getAmount() {
        return value;
    }

    public void setAmount(BigDecimal value) {
        this.value = value;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
}
