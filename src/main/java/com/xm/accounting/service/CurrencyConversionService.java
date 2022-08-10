package com.xm.accounting.service;

import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.Salary;

public interface CurrencyConversionService {

    void convert(Salary salary, Currency to);
}
