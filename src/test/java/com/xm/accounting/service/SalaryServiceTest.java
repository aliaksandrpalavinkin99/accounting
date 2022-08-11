package com.xm.accounting.service;

import com.xm.accounting.SpringTestConfig;
import com.xm.accounting.entity.*;
import com.xm.accounting.parser.SalaryParserImpl;
import com.xm.accounting.repository.SalaryRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class SalaryServiceTest {

    @Mock
    private SalaryParserImpl salaryParser;
    @Mock
    private CurrencyConversionServiceImpl conversionService;
    @Mock
    private SalaryRepository salaryRepository;
    @InjectMocks
    private SalaryServiceImpl salaryService;

    @Test
    public void givenSalaryStat_success() {
        List<Salary> salaryList = List.of(new Salary("aa",
                new Money(new BigDecimal(1000), Currency.USD),
                LocalDate.of(2022, 4, 7)),
                new Salary("aa",
                        new Money(new BigDecimal(2000), Currency.USD),
                        LocalDate.of(2022, 5, 7)));

        Mockito.when(salaryRepository.findAll()).thenReturn(salaryList);
        Mockito.when(salaryParser.isParseNeeded()).thenReturn(false);
        Mockito.doAnswer(answ -> {
            Salary salary = answ.getArgument(0);

            return new Money(salary.getMoney().getAmount().multiply(new BigDecimal(2) ), Currency.GEL);
        }).when(conversionService).convert(Mockito.any(Salary.class), Mockito.any(Currency.class));

        SalaryStatistic salaryStat = salaryService.getSalaryStatistic();

        String expectedPeriod = "ALL";
        Assert.assertEquals(expectedPeriod, salaryStat.getPeriod());
        int expectedSize = 1;
        Assert.assertEquals(expectedSize, salaryStat.getSalaries().size());
        BigDecimal expectedSalary = new BigDecimal(6000);
        Assert.assertEquals(expectedSalary, salaryStat.getSalaries().get(0).getMoney().getAmount());
    }
}
