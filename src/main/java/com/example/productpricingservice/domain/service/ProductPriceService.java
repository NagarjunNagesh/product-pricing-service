package com.example.productpricingservice.domain.service;

import java.time.LocalDateTime;

import com.example.productpricingservice.domain.model.ProductPrice;

public interface ProductPriceService {

    ProductPrice getApplicablePrice(LocalDateTime startDate, Long productId, Long brandId);
}
