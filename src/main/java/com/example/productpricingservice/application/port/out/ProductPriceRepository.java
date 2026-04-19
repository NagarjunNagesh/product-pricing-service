package com.example.productpricingservice.application.port.out;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.productpricingservice.domain.model.ProductPrice;

public interface ProductPriceRepository {

    Optional<ProductPrice> findApplicablePrice(LocalDateTime applicationDateTime, Long productId, Long brandId);
}