package com.example.productpricingservice.domain.port;

import com.example.productpricingservice.domain.model.ProductPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface ProductPriceRepository {

    List<ProductPrice> findApplicablePrices(LocalDateTime startDate, Long productId, Long brandId);
}
