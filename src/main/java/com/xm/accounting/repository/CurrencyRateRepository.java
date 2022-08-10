package com.xm.accounting.repository;

import com.xm.accounting.entity.Currency;
import com.xm.accounting.entity.CurrencyRate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyRateRepository extends CrudRepository<CurrencyRate, Long> {

    @Query(value = "SELECT TOP 1 * FROM Rates rate WHERE rate.from_currency = :from_currency " +
            "AND rate.to_currency = :to_currency " +
            "AND rate.rate_date <= :salary_date " +
            "order by rate.rate_date desc", nativeQuery = true)
    Optional<CurrencyRate> findFirstRate(@Param("salary_date") LocalDate salaryDate, @Param("from_currency") String from,
                                        @Param("to_currency") String to);
}
