package com.example.productpricingservice.domain.port;

import java.time.LocalDateTime;
import java.util.List;

import com.example.productpricingservice.domain.model.ProductPrice;

public interface ProductPriceRepository {

    List<ProductPrice> findApplicablePrices(LocalDateTime applicationDateTime, Long productId, Long brandId);
}
