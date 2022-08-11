package com.xm.accounting.dto;

public class SalaryDTO {

    private String employee;
    private MoneyDTO money;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public MoneyDTO getMoney() {
        return money;
    }

    public void setMoney(MoneyDTO money) {
        this.money = money;
    }
}
