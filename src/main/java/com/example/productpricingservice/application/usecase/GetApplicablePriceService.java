package com.example.productpricingservice.application.usecase;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;
import com.example.productpricingservice.application.port.out.ProductPriceRepository;
import com.example.productpricingservice.domain.exception.PriceNotFoundException;
import com.example.productpricingservice.domain.model.ProductPrice;

public class GetApplicablePriceService implements GetApplicablePriceUseCase {
        private static final Logger log = LoggerFactory.getLogger(GetApplicablePriceService.class);

        private final ProductPriceRepository productPriceRepository;

        public GetApplicablePriceService(ProductPriceRepository productPriceRepository) {
                this.productPriceRepository = productPriceRepository;
        }

        @Override
        public ProductPrice getApplicablePrice(LocalDateTime applicationDateTime, Long productId, Long brandId) {
                log.debug("Fetching top-1 applicable price for applicationDateTime={}, productId={}, brandId={}",
                                applicationDateTime,
                                productId, brandId);

                return productPriceRepository.findApplicablePrice(applicationDateTime, productId, brandId)
                                .orElseThrow(() -> {
                                        log.error("No applicable price found for productId={}, brandId={}, applicationDateTime={}",
                                                        productId, brandId,
                                                        applicationDateTime);
                                        return new PriceNotFoundException(
                                                        "No price found for product " + productId + " and brand "
                                                                        + brandId
                                                                        + " at " + applicationDateTime);
                                });
        }
}