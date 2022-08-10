package com.xm.accounting.entity;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvRecurse;

import javax.persistence.Column;
import javax.persistence.Table;
import java.time.LocalDate;

@javax.persistence.Entity
@Table(name = "Salaries")
public class Salary extends Entity {

    @CsvBindByName(column = "Employee")
    @Column(name = "EmployeeName")
    private String employeeName;
    @CsvRecurse
    private Money money;

    @Column(name = "SalaryDate")
    private LocalDate salaryDate;

    public Salary() {
    }

    public Salary(String employee, Money money, LocalDate salaryDate) {
        this.employeeName = employee;
        this.money = money;
        this.salaryDate = salaryDate;
    }

    public String getEmployee() {
        return employeeName;
    }

    public void setEmployee(String employee) {
        this.employeeName = employee;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public LocalDate getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(LocalDate salaryDate) {
        this.salaryDate = salaryDate;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
