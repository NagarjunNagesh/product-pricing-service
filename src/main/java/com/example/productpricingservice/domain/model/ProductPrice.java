package com.example.productpricingservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductPrice(
        Long brandId,
        Long productId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priority,
        BigDecimal price,
        String currency) {
}
