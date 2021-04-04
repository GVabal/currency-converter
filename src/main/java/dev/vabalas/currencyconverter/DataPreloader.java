package dev.vabalas.currencyconverter;

import dev.vabalas.currencyconverter.exception.FailedToReadCsvDataException;
import dev.vabalas.currencyconverter.model.CurrencyType;
import dev.vabalas.currencyconverter.repository.ExchangeRatesRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;


@Component
@AllArgsConstructor
public class DataPreloader {

    private final ExchangeRatesRepository exchangeRatesRepository;
    private final ResourceLoader resourceLoader;

    @PostConstruct
    public void initializeData() {
        Resource resource = resourceLoader.getResource("classpath:data.csv");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineParts = line.split(",");
                exchangeRatesRepository.save(CurrencyType.valueOf(lineParts[0]),
                        BigDecimal.valueOf(Double.parseDouble(lineParts[1])));
            }
        } catch (IOException e) {
            throw new FailedToReadCsvDataException(e.getMessage());
        }
    }

}
