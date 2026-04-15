package com.example.productpricingservice.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductPrice(
        Long productId,
        Long brandId,
        Long priceList,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Integer priority,
        BigDecimal price,
        String currency) {
}
