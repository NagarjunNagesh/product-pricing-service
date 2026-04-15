package com.example.productpricingservice.domain.service;

import java.time.LocalDateTime;

import com.example.productpricingservice.domain.model.ProductPrice;

public interface ProductPriceService {

    ProductPrice getApplicablePrice(LocalDateTime applicationDateTime, Long productId, Long brandId);
}
