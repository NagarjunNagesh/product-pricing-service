package com.example.productpricingservice.domain.service;

import com.example.productpricingservice.domain.exception.PriceNotFoundException;
import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.domain.port.ProductPriceRepository;

import java.time.LocalDateTime;
import java.util.List;

public class DomainProductPriceService implements ProductPriceService {

    private final ProductPriceRepository productPriceRepository;
    private final PriceSelectionPolicy priceSelectionPolicy;

    public DomainProductPriceService(
            ProductPriceRepository productPriceRepository,
            PriceSelectionPolicy priceSelectionPolicy) {
        this.productPriceRepository = productPriceRepository;
        this.priceSelectionPolicy = priceSelectionPolicy;
    }

    @Override
    public ProductPrice getApplicablePrice(LocalDateTime startDate, Long productId, Long brandId) {
        List<ProductPrice> candidates = productPriceRepository.findApplicablePrices(startDate, productId, brandId);

        return priceSelectionPolicy.selectApplicable(candidates)
                .orElseThrow(() -> new PriceNotFoundException(
                        "No price found for product " + productId + " and brand " + brandId
                                + " at " + startDate));
    }
}
