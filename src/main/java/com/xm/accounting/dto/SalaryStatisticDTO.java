package com.xm.accounting.dto;

import java.util.List;

public class SalaryStatisticDTO {
    private String period;
    private List<SalaryDTO> salaries;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public List<SalaryDTO> getSalaries() {
        return salaries;
    }

    public void setSalaries(List<SalaryDTO> salaries) {
        this.salaries = salaries;
    }
}
