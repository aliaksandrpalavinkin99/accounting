package com.xm.accounting;

import com.xm.accounting.parser.CurrencyRateParser;
import com.xm.accounting.parser.CurrencyRateParserImpl;
import com.xm.accounting.parser.SalaryParser;
import com.xm.accounting.parser.SalaryParserImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringConfig {

    @Bean
    public SalaryParser getSalaryParser() {
        return new SalaryParserImpl();
    }

    @Bean
    public CurrencyRateParser getCurrencyRateParser() {
        return new CurrencyRateParserImpl();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.xm.accounting.controller.v1"))
                .paths(PathSelectors.any())
                .build();
    }
}
