package com.example.productpricingservice.application.port.in;

import java.time.LocalDateTime;

import com.example.productpricingservice.domain.model.ProductPrice;

public interface GetApplicablePriceUseCase {

    ProductPrice getApplicablePrice(LocalDateTime applicationDateTime, Long productId, Long brandId);
}