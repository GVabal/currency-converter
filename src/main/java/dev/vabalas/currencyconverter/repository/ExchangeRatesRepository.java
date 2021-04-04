package dev.vabalas.currencyconverter.repository;

import dev.vabalas.currencyconverter.model.CurrencyType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ExchangeRatesRepository {

    private final Map<CurrencyType, BigDecimal> data = new EnumMap<>(CurrencyType.class);

    public void save(CurrencyType type, BigDecimal rate) {
        data.put(type, rate);
    }

    public Optional<BigDecimal> findRateByType(CurrencyType type) {
        return Optional.of(data.get(type));
    }
}
