package com.xm.accounting;

import com.xm.accounting.parser.CurrencyRateParser;
import com.xm.accounting.parser.CurrencyRateParserImpl;
import com.xm.accounting.parser.SalaryParser;
import com.xm.accounting.parser.SalaryParserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringTestConfig {

    @Bean
    public SalaryParser getSalaryParser() {
        return new SalaryParserImpl();
    }

    @Bean
    public CurrencyRateParser getCurrencyRateParser() {
        return new CurrencyRateParserImpl();
    }
}
