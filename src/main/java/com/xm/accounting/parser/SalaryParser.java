package com.xm.accounting.parser;

import com.xm.accounting.entity.Salary;

import java.time.Month;
import java.util.List;

public interface SalaryParser extends CSVParser<Salary> {

    boolean isParseNeeded(Month month, Integer year);

    List<Salary> parse(Month month, Integer year);
}
