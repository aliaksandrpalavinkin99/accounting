package com.xm.accounting.service;

import com.xm.accounting.dto.SortType;
import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.Salary;
import com.xm.accounting.entity.SalaryStatistic;
import com.xm.accounting.parser.SalaryParser;
import com.xm.accounting.repository.SalaryRepository;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SalaryServiceImpl implements SalaryService {

    @Autowired
    private CurrencyConversionService conversionService;
    @Autowired
    private SalaryParser salaryParser;
    @Autowired
    private SalaryRepository salaryRepository;

    @Override
    public SalaryStatistic getSalariesByMonthAndYear(Month month, Integer year, SortType sortType) {
        SalaryStatistic salaryStatistic = new SalaryStatistic(month.name());
        List<Salary> salaries;

        if (salaryParser.isParseNeeded(month, year)) {
            salaries = salaryParser.parse(month, year);
            saveSalaries(salaries);
        }

        salaries = salaryRepository.findSalariesByMonthAndYear(month.getValue(), year);

        salaries.forEach(salary -> salary.setMoney(conversionService.convert(salary, Currency.GEL)));
        salaryStatistic.setSalaries(salaries);

        salaryStatistic.sort(sortType);

        return salaryStatistic;
    }

    @Override
    public SalaryStatistic getSalaryStatistic() {
        SalaryStatistic salaryStatistic = new SalaryStatistic("ALL");
        List<Salary> salaries;

        if (salaryParser.isParseNeeded()) {
            salaries = salaryParser.parse();
            saveSalaries(salaries);
        }

        salaries = IterableUtils.toList(salaryRepository.findAll());

        salaries.forEach(salary -> salary.setMoney(conversionService.convert(salary, Currency.GEL)));
        salaries = sumSalaryForEmpls(salaries);
        salaryStatistic.setSalaries(salaries);
        return salaryStatistic;
    }

    private void saveSalaries(List<Salary> salaries) {
        salaryRepository.saveAll(salaries);
    }

    private List<Salary> sumSalaryForEmpls(List<Salary> salaries) {
        List<Salary> finalSalary = new ArrayList<>();

        salaries.forEach(salary -> {
            Optional<Salary> matchedSalary = finalSalary
                    .stream().filter(x -> Objects.equals(salary.getEmployee(), x.getEmployee())).findFirst();
            matchedSalary
                    .ifPresentOrElse(sal -> sal.getMoney()
                            .setAmount(sal.getMoney().getAmount()
                                    .add(salary.getMoney().getAmount())), () -> finalSalary.add(salary));
        });

        return finalSalary;
    }
}
