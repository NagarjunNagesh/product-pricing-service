package com.example.productpricingservice.domain.service;

import com.example.productpricingservice.domain.model.ProductPrice;

import java.time.LocalDateTime;

public interface ProductPriceService {

    ProductPrice getApplicablePrice(LocalDateTime startDate, Long productId, Long brandId);
}
