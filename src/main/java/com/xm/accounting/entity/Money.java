package com.xm.accounting.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvIgnore;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@javax.persistence.Embeddable
public class Money {

    @CsvBindByName(column = "Salary")
    @Column(name = "Amount")
    private BigDecimal value;
    @CsvIgnore
    @Column(name = "Currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    public Money() {
    }

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

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
