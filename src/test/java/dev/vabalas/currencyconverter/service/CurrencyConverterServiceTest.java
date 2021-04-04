package dev.vabalas.currencyconverter.service;

import dev.vabalas.currencyconverter.repository.ExchangeRatesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import static dev.vabalas.currencyconverter.model.CurrencyType.*;
import static dev.vabalas.currencyconverter.service.CurrencyConverterService.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CurrencyConverterServiceTest {

    @InjectMocks
    CurrencyConverterService service;

    @Mock
    ExchangeRatesRepository repository;

    @Test
    void returnsNumberWithCorrectNumberOfDigitsAfterDecimalPoint() {
        BigDecimal result = service.convert(10, EUR, EUR);

        assertThat(result.scale()).isEqualTo(SCALE);
    }

    @Test
    void convertsFromXToX() {
        BigDecimal result = service.convert(10, EUR, EUR);

        assertThat(result).isEqualTo(BigDecimal.TEN.setScale(SCALE, RoundingMode.HALF_DOWN));
    }

    @Test
    void convertsFromXToEur() {
        when(repository.findRateByType(any())).thenReturn(Optional.of(BigDecimal.TEN));

        BigDecimal result = service.convert(10, USD, EUR);

        assertThat(result).isEqualTo(BigDecimal.valueOf(100).setScale(SCALE, RoundingMode.HALF_DOWN));
    }

    @Test
    void convertsFromEurToX() {
        when(repository.findRateByType(any())).thenReturn(Optional.of(BigDecimal.TEN));

        BigDecimal result = service.convert(10, EUR, USD);

        assertThat(result).isEqualTo(BigDecimal.ONE.setScale(SCALE, RoundingMode.HALF_DOWN));
    }

    @Test
    void convertsFromXToY() {
        when(repository.findRateByType(any())).thenReturn(Optional.of(BigDecimal.TEN));

        BigDecimal result = service.convert(10, USD, GBP);

        assertThat(result).isEqualTo(BigDecimal.TEN.setScale(SCALE, RoundingMode.HALF_DOWN));
    }
}
