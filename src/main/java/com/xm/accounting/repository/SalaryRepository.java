package com.xm.accounting.repository;

import com.xm.accounting.entity.Salary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalaryRepository extends CrudRepository<Salary, Long> {

    @Query(value = "SELECT s FROM Salary s WHERE MONTH(s.salaryDate) = :month " +
            "AND YEAR(s.salaryDate) = :year")
    List<Salary> findSalariesByMonthAndYear(@Param("month") int month, @Param("year") int year);
}
