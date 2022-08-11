package com.xm.accounting.controller.v1;

import com.xm.accounting.controller.EndPointPath;
import com.xm.accounting.dto.SalaryStatisticDTO;
import com.xm.accounting.dto.SortType;
import com.xm.accounting.entity.SalaryStatistic;
import com.xm.accounting.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Month;

@RestController
@RequestMapping(value = EndPointPath.SALARY)
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @ApiOperation(value = "Return sum of employees salaries for all time in GEL according at the exchange rate by months")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryStatisticDTO getSalaryGel() {
        SalaryStatistic salaryStatistic = salaryService.getSalaryStatistic();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(salaryStatistic, SalaryStatisticDTO.class);
    }

    @ApiOperation(value = "Returns salaries of employees in selected month and year")
    @GetMapping(value = "/{month}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryStatisticDTO getSalaryMonthGel(@PathVariable @ApiParam(name = "month", value = "selected month") Month month,
                                                @PathVariable @ApiParam(name = "year", value = "selected year") Integer year,
                                                @RequestParam(required = false)
                                                @ApiParam(name = "sortType",
                                                        value = "Not required param, need to set sort type(ASC-usual sort, DESC - reversed)")
                                                SortType sortType) {
        SalaryStatistic salaryStatistic = salaryService.getSalariesByMonthAndYear(month, year, sortType);
        ModelMapper modelMapper = new ModelMapper();

        return modelMapper.map(salaryStatistic, SalaryStatisticDTO.class);
    }
}
