package com.xm.accounting.service;

import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.CurrencyRate;
import com.xm.accounting.entity.Salary;
import com.xm.accounting.exception.HistoryRateNotFoundException;
import com.xm.accounting.parser.CurrencyRateParser;
import com.xm.accounting.repository.CurrencyRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    @Autowired
    private CurrencyRateParser currencyRateParser;
    @Autowired
    private CurrencyRateRepository rateRepository;

    @Override
    public void convert(Salary salary, Currency to) {
        if (Objects.equals(salary.getMoney().getCurrency(), to)) {
            return;
        }

        BigDecimal rate = getSalaryRateByMonth(salary.getSalaryDate(), salary.getMoney().getCurrency(), to);

        salary.getMoney().setCurrency(to);
        salary.getMoney().setAmount(salary.getMoney().getAmount().multiply(rate));
    }

    private BigDecimal getSalaryRateByMonth(LocalDate salaryDate, Currency from, Currency to) {
        parseData();

        Optional<CurrencyRate> currencyRate = rateRepository.findFirstRate(salaryDate, from.toString(), to.toString());

        return currencyRate.orElseThrow(() -> {
            throw new HistoryRateNotFoundException("The salary rate is absent");
        }).getRate();
    }

    private void parseData() {
        if (currencyRateParser.isParseNeeded()) {
            List<CurrencyRate> currencyRates = currencyRateParser.parse();
            saveRates(currencyRates);
        }
    }

    private void saveRates(List<CurrencyRate> rates) {
        rateRepository.saveAll(rates);
    }
}
