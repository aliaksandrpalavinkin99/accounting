package com.xm.accounting.service;

import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.Salary;
import com.xm.accounting.entity.SalaryStat;
import com.xm.accounting.parser.SalaryParser;
import com.xm.accounting.repository.SalaryRepository;
import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.internal.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {
    private static final Integer SALARY_DAY = 7;
    @Autowired
    private CurrencyConversionService conversionService;
    @Autowired
    private SalaryParser salaryParser;
    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public SalaryStat getSalaryStatByMonthAndYear(Month month, Integer year) {
        SalaryStat salaryStat = new SalaryStat(month.name());
        List<Salary> salaries;

        if (salaryParser.isParseNeeded(month, year)) {
            salaries = salaryParser.parse(month, year);
            saveSalaries(salaries);
        }

        salaries = salaryRepository.findSalariesByMonthAndYear(month.getValue(), year);

        salaries.forEach(salary -> conversionService.convert(salary, Currency.GEL));
        salaryStat.setSalaries(salaries);

        return salaryStat;
    }

    @Override
    public SalaryStat getSalaryStat() {
        SalaryStat salaryStat = new SalaryStat("ALL");
        List<Salary> salaries;

        if (salaryParser.isParseNeeded()) {
            salaries = salaryParser.parse();
            saveSalaries(salaries);
        }

        salaries = IterableUtils.toList(salaryRepository.findAll());

        salaries.forEach(salary -> conversionService.convert(salary, Currency.GEL));
        salaries = sumSalaryForEmpls(salaries);
        salaryStat.setSalaries(salaries);
        return salaryStat;
    }

    private void saveSalaries(List<Salary> salaries) {
        salaryRepository.saveAll(salaries);
    }

    private List<Salary> sumSalaryForEmpls(List<Salary> salaries) {
        List<Salary> finalSalary = new ArrayList<>();

        salaries.forEach(salary -> {
            Optional<Salary> matchedSalary = finalSalary.stream().filter(x -> Objects.equals(salary.getEmployee(), x.getEmployee())).findFirst();

            matchedSalary.ifPresentOrElse(sal -> sal.getMoney().setAmount(sal.getMoney().getAmount().add(salary.getMoney().getAmount())), () -> finalSalary.add(salary));
        });

        return finalSalary;
    }
}
