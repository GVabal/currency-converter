package dev.vabalas.currencyconverter.service;

import dev.vabalas.currencyconverter.exception.ExchangeRateNotFoundException;
import dev.vabalas.currencyconverter.model.CurrencyType;
import dev.vabalas.currencyconverter.repository.ExchangeRatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@AllArgsConstructor
public class CurrencyConverterService {

    public static final int SCALE = 18;
    private final ExchangeRatesRepository exchangeRatesRepository;

    public BigDecimal convert(double amount, CurrencyType from, CurrencyType to) {
        if (from.equals(to)) {
            return BigDecimal.valueOf(amount)
                    .setScale(SCALE, RoundingMode.HALF_DOWN);
        }
        if (from.equals(CurrencyType.EUR)) {
            BigDecimal toRate = getExchangeRate(to);
            return BigDecimal.valueOf(amount)
                    .setScale(SCALE, RoundingMode.HALF_DOWN)
                    .divide(toRate, RoundingMode.HALF_DOWN);
        }
        if (to.equals(CurrencyType.EUR)) {
            BigDecimal fromRate = getExchangeRate(from);
            return BigDecimal.valueOf(amount)
                    .setScale(SCALE, RoundingMode.HALF_DOWN)
                    .multiply(fromRate);
        }
        BigDecimal fromRate = getExchangeRate(from);
        BigDecimal toRate = getExchangeRate(to);
        return BigDecimal.valueOf(amount)
                .setScale(SCALE, RoundingMode.HALF_DOWN)
                .multiply(fromRate)
                .divide(toRate, RoundingMode.HALF_DOWN);
    }

    private BigDecimal getExchangeRate(CurrencyType type) {
        return exchangeRatesRepository.findRateByType(type)
                .orElseThrow(() -> new ExchangeRateNotFoundException("No exchange rate for " + type));
    }
}
