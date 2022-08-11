package com.xm.accounting.service;

import com.xm.accounting.SpringTestConfig;
import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.CurrencyRate;
import com.xm.accounting.entity.Money;
import com.xm.accounting.entity.Salary;
import com.xm.accounting.parser.CurrencyRateParserImpl;
import com.xm.accounting.repository.CurrencyRateRepository;
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
import java.util.Optional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringTestConfig.class)
public class CurrencyConversionServiceTest {

    @Mock
    private CurrencyRateParserImpl currencyRateParser;
    @Mock
    private CurrencyRateRepository rateRepository;
    @InjectMocks
    private CurrencyConversionServiceImpl conversionService;

    @Test
    public void convertSalary_withExistingSalaryRate_Success() {
        Salary salary = new Salary();
        salary.setSalaryDate(LocalDate.of(2022,4, 7));
        salary.setMoney(new Money(new BigDecimal(1000), Currency.USD));
        salary.setEmployee("Test Test");

        Mockito.when(currencyRateParser.isParseNeeded()).thenReturn(false);
        Mockito.when(rateRepository.findFirstRate(Mockito.any(LocalDate.class),
                Mockito.any(String.class), Mockito.any(String.class)))
                .thenReturn(Optional.of(new CurrencyRate(LocalDate.of(2022,4, 7),
                new BigDecimal("3.08"), Currency.USD, Currency.GEL)));

        Money money = conversionService.convert(salary, Currency.GEL);

        BigDecimal expectedSalary = new BigDecimal("3080.00");
        Assert.assertEquals(expectedSalary, money.getAmount());
    }

    @Test
    public void convertSalary_withParsing_Success() {
        Salary salary = new Salary();
        salary.setSalaryDate(LocalDate.of(2022,4, 7));
        salary.setMoney(new Money(new BigDecimal(1000), Currency.USD));
        salary.setEmployee("Test Test");

        List<CurrencyRate> rates =
                List.of(new CurrencyRate(LocalDate.of(2022,4, 5),
                        new BigDecimal("2"), Currency.USD, Currency.GEL),
                        new CurrencyRate(LocalDate.of(2022,4, 8),
                                new BigDecimal("3.08"), Currency.USD, Currency.GEL));

        Mockito.when(currencyRateParser.isParseNeeded()).thenReturn(true);
        Mockito.when(currencyRateParser.parse()).thenReturn(rates);
        Mockito.when(rateRepository.saveAll(Mockito.any())).thenReturn(rates);
        Mockito.when(rateRepository.findFirstRate(Mockito.any(LocalDate.class),
                Mockito.any(String.class), Mockito.any(String.class))).thenReturn(Optional.of(rates.get(0)));

        Money money = conversionService.convert(salary, Currency.GEL);

        BigDecimal expectedSalary = new BigDecimal(2000);
        Assert.assertEquals(expectedSalary, money.getAmount());
    }

    @Test
    public void convertSalary_withAbsentSalaryRate_throwException() {
        Salary salary = new Salary();
        salary.setSalaryDate(LocalDate.of(2022,4, 7));
        salary.setMoney(new Money(new BigDecimal(1000), Currency.USD));
        salary.setEmployee("Test Test");

        List<CurrencyRate> rates = List.of();

        Mockito.when(currencyRateParser.parse()).thenReturn(rates);

        Assert.assertThrows(RuntimeException.class, () -> conversionService.convert(salary, Currency.GEL));
    }
}
