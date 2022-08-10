package com.xm.accounting.entity;

import java.util.List;

public class SalaryStat {
    private String period;
    private List<Salary> salaries;

    public SalaryStat(String period) {
        this.period = period;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<Salary> salaries) {
        this.salaries = salaries;
    }
}
