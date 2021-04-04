package dev.vabalas.currencyconverter.controller;

import dev.vabalas.currencyconverter.model.CurrencyType;
import dev.vabalas.currencyconverter.service.CurrencyConverterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/currency-converter")
@AllArgsConstructor
public class CurrencyConverterController {

    private final CurrencyConverterService currencyConverterService;

    @GetMapping("convert")
    public BigDecimal convertAmount(@RequestParam double amount, @RequestParam CurrencyType from,
                                    @RequestParam CurrencyType to) {
        return currencyConverterService.convert(amount, from, to);
    }
}
