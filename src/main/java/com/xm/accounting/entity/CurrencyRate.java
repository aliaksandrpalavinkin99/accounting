package com.xm.accounting.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "Rates")
public class CurrencyRate extends Entity {

    @CsvDate(value = "M/d/yyyy EEEE")
    @CsvBindByName(column = "Date")
    @Column(name = "RateDate")
    private LocalDate date;
    @CsvBindByName(column = "Rate")
    @Column(name = "RateValue")
    private BigDecimal rate;
    @CsvBindByName(column = "ISO Code From")
    @Column(name = "FromCurrency")
    @Enumerated(EnumType.STRING)
    private Currency from;
    @CsvBindByName(column = "ISO Code To")
    @Column(name = "ToCurrency")
    @Enumerated(EnumType.STRING)
    private Currency to;

    public CurrencyRate() {
    }

    public CurrencyRate(LocalDate date, BigDecimal rate, Currency from, Currency to) {
        this.date = date;
        this.rate = rate;
        this.from = from;
        this.to = to;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Currency getFrom() {
        return from;
    }

    public void setFrom(Currency from) {
        this.from = from;
    }

    public Currency getTo() {
        return to;
    }

    public void setTo(Currency to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyRate that = (CurrencyRate) o;
        return Objects.equals(date, that.date) && Objects.equals(rate, that.rate) && from == that.from && to == that.to;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rate, from, to);
    }
}
