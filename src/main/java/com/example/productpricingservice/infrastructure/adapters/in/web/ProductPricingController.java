package com.example.productpricingservice.infrastructure.adapters.in.web;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productpricingservice.infrastructure.adapters.in.web.dto.ProductPriceResponse;
import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;
import com.example.productpricingservice.domain.model.ProductPrice;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/prices")
@Tag(name = "Prices", description = "Query applicable product prices by date, product, and brand")
public class ProductPricingController {
    private static final Logger log = LoggerFactory.getLogger(ProductPricingController.class);

    private final GetApplicablePriceUseCase getApplicablePriceUseCase;

    public ProductPricingController(GetApplicablePriceUseCase getApplicablePriceUseCase) {
        this.getApplicablePriceUseCase = getApplicablePriceUseCase;
    }

    @GetMapping
    @Operation(summary = "Get the applicable price for product and brand on a given date")
    public ProductPriceResponse getApplicablePrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDateTime,
            @RequestParam Long productId,
            @RequestParam Long brandId) {
        log.info("getApplicablePrice called with applicationDateTime={}, productId={}, brandId={}", applicationDateTime, productId,
                brandId);

        ProductPrice selectedPrice = getApplicablePriceUseCase.getApplicablePrice(applicationDateTime, productId, brandId);

        log.info("Selected price for productId={}, brandId={} -> {}", productId, brandId, selectedPrice);

        return ProductPriceResponse.builder()
                .productId(selectedPrice.productId())
                .brandId(selectedPrice.brandId())
                .priceList(selectedPrice.priceList())
                .startDate(selectedPrice.startDate())
                .endDate(selectedPrice.endDate())
                .price(selectedPrice.price())
                .currency(selectedPrice.currency())
                .build();
    }
}
