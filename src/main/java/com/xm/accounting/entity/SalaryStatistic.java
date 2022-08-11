package com.xm.accounting.entity;

import com.xm.accounting.dto.SortType;

import java.util.Comparator;
import java.util.List;

public class SalaryStatistic {
    private String period;
    private List<Salary> salaries;

    public SalaryStatistic(String period) {
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

    public void sort(SortType sortType) {
        if (sortType.equals(SortType.DESC)) {
            salaries.sort(Comparator.comparing(Salary::getAmount).reversed());
        } else {
            salaries.sort(Comparator.comparing(Salary::getAmount));
        }
    }
}
