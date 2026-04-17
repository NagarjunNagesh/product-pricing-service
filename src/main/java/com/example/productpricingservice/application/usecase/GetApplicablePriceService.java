package com.example.productpricingservice.application.usecase;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;
import com.example.productpricingservice.application.port.out.ProductPriceRepository;
import com.example.productpricingservice.domain.exception.PriceNotFoundException;
import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.domain.service.PriceSelectionPolicy;

public class GetApplicablePriceService implements GetApplicablePriceUseCase {
    private static final Logger log = LoggerFactory.getLogger(GetApplicablePriceService.class);

    private final ProductPriceRepository productPriceRepository;
    private final PriceSelectionPolicy priceSelectionPolicy;

    public GetApplicablePriceService(
            ProductPriceRepository productPriceRepository,
            PriceSelectionPolicy priceSelectionPolicy) {
        this.productPriceRepository = productPriceRepository;
        this.priceSelectionPolicy = priceSelectionPolicy;
    }

    @Override
    public ProductPrice getApplicablePrice(LocalDateTime applicationDateTime, Long productId, Long brandId) {
        log.info("Started fetching eligible candidates for applicationDateTime {}, productId {} and brandId {}", applicationDateTime,
                productId, brandId);

        List<ProductPrice> candidates = productPriceRepository.findApplicablePrices(applicationDateTime, productId, brandId);

        return priceSelectionPolicy.selectApplicable(candidates)
                .orElseThrow(() -> {
                    log.error("No applicable price found for product {} and brand {} at {}", productId, brandId,
                            applicationDateTime);
                    return new PriceNotFoundException(
                            "No price found for product " + productId + " and brand " + brandId
                                    + " at " + applicationDateTime);
                });
    }
}