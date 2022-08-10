package com.xm.accounting.controller.v1;

import com.xm.accounting.dto.SalaryDTO;
import com.xm.accounting.dto.SalaryStatDTO;
import com.xm.accounting.dto.SortType;
import com.xm.accounting.controller.EndPointPath;
import com.xm.accounting.entity.SalaryStat;
import com.xm.accounting.service.SalaryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = EndPointPath.SALARY)
public class SalaryEndPoint {

    @Autowired
    private SalaryService salaryService;

    @ApiOperation(value = "Return sum of employees salaries for all time in GEL according at the exchange rate by months")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryStatDTO getSalaryGel() {
        SalaryStat salaryStat = salaryService.getSalaryStat();
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(salaryStat, SalaryStatDTO.class);
    }

    @ApiOperation(value = "Returns salaries of employees in selected month and year")
    @GetMapping(value = "/{month}/{year}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SalaryStatDTO getSalaryMonthGel(@PathVariable @ApiParam(name = "month", value = "selected month") Month month,
                                           @PathVariable @ApiParam(name = "year", value = "selected year")   Integer year,
                                           @RequestParam(required = false)
                                           @ApiParam(name = "sortType",
                                                     value = "Not required param, need to set sort type(ASC-usual sort, DESC - reversed)")
                                           SortType sortType) {
        SalaryStat salaryStat = salaryService.getSalaryStatByMonthAndYear(month, year);
        ModelMapper modelMapper = new ModelMapper();
        SalaryStatDTO salaryStatDTO = modelMapper.map(salaryStat, SalaryStatDTO.class);
        
        sortSalary(salaryStatDTO.getSalaries(), sortType);
        
        return salaryStatDTO;
    }
    
    private void sortSalary(List<SalaryDTO> salaryDTOList, SortType sortType) {
        if (Objects.nonNull(sortType)) {
            if (SortType.ASC.equals(sortType)) {
                salaryDTOList.sort(Comparator.comparing(salary -> salary.getMoney().getAmount()));
            } else {
                salaryDTOList
                        .sort(Comparator.comparing(salary -> salary.getMoney().getAmount(), Comparator.reverseOrder()));
            }
        }
    }
}
