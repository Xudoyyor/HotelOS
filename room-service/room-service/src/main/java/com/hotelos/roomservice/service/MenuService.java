package com.hotelos.roomservice.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Xona xizmati menyusi (in-memory katalog).
 * LinkedHashMap - kiritilish tartibini saqlovchi assotsiativ massiv (DATA STRUCTURE).
 */
@Service
public class MenuService {

    private final Map<String, BigDecimal> menu = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        menu.put("Choy", BigDecimal.valueOf(15000));
        menu.put("Qahva", BigDecimal.valueOf(25000));
        menu.put("Suv", BigDecimal.valueOf(8000));
        menu.put("Sendvich", BigDecimal.valueOf(45000));
        menu.put("Milliy taom", BigDecimal.valueOf(75000));
        menu.put("Shirinlik", BigDecimal.valueOf(35000));
    }

    public Map<String, BigDecimal> getMenu() {
        return menu;
    }

    private BigDecimal priceOf(String item) {
        return menu.getOrDefault(item, BigDecimal.ZERO);
    }

    /** Buyurtmadagi barcha taomlar narxini yig'indisi. */
    public BigDecimal calculateTotal(List<String> items) {
        return items.stream()
                .map(this::priceOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /** Inson o'qiy oladigan tavsif: "Choy, Qahva". */
    public String buildDescription(List<String> items) {
        return items.stream().collect(Collectors.joining(", "));
    }
}