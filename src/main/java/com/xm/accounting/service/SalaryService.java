package com.xm.accounting.service;

import com.xm.accounting.entity.SalaryStat;

import java.time.Month;

public interface SalaryService {

    SalaryStat getSalaryStatByMonthAndYear(Month month, Integer year);

    SalaryStat getSalaryStat();


}
