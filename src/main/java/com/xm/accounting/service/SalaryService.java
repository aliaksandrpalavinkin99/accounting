package com.xm.accounting.service;

import com.xm.accounting.dto.SortType;
import com.xm.accounting.entity.SalaryStatistic;

import java.time.Month;

public interface SalaryService {

    SalaryStatistic getSalariesByMonthAndYear(Month month, Integer year, SortType sortType);

    SalaryStatistic getSalaryStatistic();


}
