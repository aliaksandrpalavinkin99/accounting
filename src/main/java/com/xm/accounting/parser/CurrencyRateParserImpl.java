package com.xm.accounting.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.accounting.entity.CurrencyRate;
import com.xm.accounting.exception.HistoryRateNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class CurrencyRateParserImpl implements CurrencyRateParser {

    private final Logger logger = LogManager.getLogger(CurrencyRateParserImpl.class);
    private static final String FILE_PATH = "HistoryExchangeReport.csv";

    @Override
    public boolean isParseNeeded() {
        try {
            return Files.exists(new ClassPathResource(FILE_PATH).getFile().toPath());
        } catch (IOException e) {
            return false;
        }
    }

    public List<CurrencyRate> parse() {
        List<CurrencyRate> currencyRates = new ArrayList<>();

        try {
            File file = new ClassPathResource(FILE_PATH).getFile();
            FileReader reader = new FileReader(file);

            currencyRates.addAll(new CsvToBeanBuilder<CurrencyRate>(reader)
                    .withType(CurrencyRate.class)
                    .build()
                    .parse()
                    .stream()
                    .toList());

            reader.close();

            Files.move(file.toPath(), file.toPath().resolveSibling("+" + file.getName()),
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new HistoryRateNotFoundException("History rate file is absent");
        }

        return currencyRates;
    }
}
