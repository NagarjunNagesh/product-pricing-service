package com.example.productpricingservice.domain.service;

import com.example.productpricingservice.domain.model.ProductPrice;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PriceSelectionPolicy {

    private static final Comparator<ProductPrice> PRIORITY_RULE =
            Comparator.comparing(ProductPrice::priority)
                    .thenComparing(ProductPrice::startDate);

    public Optional<ProductPrice> selectApplicable(List<ProductPrice> candidates) {
        return candidates.stream().max(PRIORITY_RULE);
    }
}
