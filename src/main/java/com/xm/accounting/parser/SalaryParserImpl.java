package com.xm.accounting.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.Salary;
import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SalaryParserImpl implements SalaryParser {
    private final Logger logger = LogManager.getLogger(SalaryParserImpl.class);
    private static final String FILE_PATH_MONTH = "salary/EmployeeSalary%s%s.csv";
    private static final String FILE_PATH = "salary";
    private static final String FILE_PATTERN = "EmployeeSalary";
    private static final Integer SALARY_DATE = 7;
    private static final Integer MONTH_START = 14;
    private static final Integer MONTH_END = 8;
    private static final Integer YEAR_START = 8;
    private static final Integer YEAR_END = 4;


    @Override
    public boolean isParseNeeded() {
        try {
            File[] files = new ClassPathResource(FILE_PATH).getFile().listFiles();

            for (File file : files) {
                if (file.getName().startsWith(FILE_PATTERN)) {
                    return true;
                }
            }

            return false;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Salary> parse() {
        List<Salary> salaries = new ArrayList<>();

        try {
            File[] files = new ClassPathResource(FILE_PATH).getFile().listFiles();

            if (files != null) {
                for (File file : files) {
                    if (file.getName().startsWith(FILE_PATTERN)) {
                        FileReader reader = new FileReader(file);

                        salaries.addAll(new CsvToBeanBuilder<Salary>(reader)
                                .withType(Salary.class)
                                .build()
                                .parse());

                        salaries.forEach(salary -> {
                            salary.getMoney().setCurrency(Currency.USD);
                            salary.setSalaryDate(buildSalaryDate(file.getName()));
                        });

                        reader.close();

                        Files.move(file.toPath(), file.toPath().resolveSibling("+" + file.getName()),
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return salaries;
    }

    @Override
    public boolean isParseNeeded(Month month, Integer year) {
        String path = String.format(FILE_PATH_MONTH, month.getDisplayName(TextStyle.FULL, Locale.ENGLISH), year);

        try {
            return Files.exists(new ClassPathResource(path).getFile().toPath());
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List<Salary> parse(Month month, Integer year) {
        List<Salary> salaries = new ArrayList<>();
        String path = String.format(FILE_PATH_MONTH, month.getDisplayName(TextStyle.FULL, Locale.ENGLISH), year);

        try {
            File file = new ClassPathResource(path).getFile();
            FileReader reader = new FileReader(file);

            salaries = new CsvToBeanBuilder<Salary>(reader)
                    .withType(Salary.class)
                    .build()
                    .parse();

            salaries.forEach(salary -> {
                salary.getMoney().setCurrency(Currency.USD);
                salary.setSalaryDate(LocalDate.of(year, month.getValue(), SALARY_DATE));
            });

            reader.close();

            Files.move(file.toPath(), file.toPath().resolveSibling("+" + file.getName()),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error(String.format("Couldn't find file: %s", path));
        }

        return salaries;
    }

    private LocalDate buildSalaryDate(String fileName) {
        String month = fileName.substring(MONTH_START, fileName.length() - MONTH_END);
        String year = fileName.substring(fileName.length() - YEAR_START, fileName.length() - YEAR_END);

        return LocalDate.of(Integer.parseInt(year), Month.valueOf(month.toUpperCase()).getValue(), SALARY_DATE);
    }
}
