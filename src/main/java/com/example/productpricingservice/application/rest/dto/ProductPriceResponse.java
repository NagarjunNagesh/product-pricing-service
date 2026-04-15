package com.example.productpricingservice.application.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "Price information applicable for the given query parameters")
public record ProductPriceResponse(
        @Schema(example = "35455") Long productId,
        @Schema(example = "1") Long brandId,
        @Schema(example = "2") Long priceList,
        @Schema(example = "2020-06-14T15:00:00") LocalDateTime startDate,
        @Schema(example = "2020-06-14T18:30:00") LocalDateTime endDate,
        @Schema(example = "25.45") BigDecimal price,
        @Schema(example = "EUR") String currency) {
}
