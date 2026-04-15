package com.example.productpricingservice.domain.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.example.productpricingservice.domain.model.ProductPrice;

/***
 * Implements the price selection policy to select the most applicable price from a list of candidates.
 *
 * The selection is based on the following rules:
 *  1. The price with the highest priority is selected.
 *  2. If multiple prices have the same priority, the one with the latest start date is selected.
 *
 * if no candidates are provided, an empty Optional is returned. The caller is responsible for handling the case where no applicable price is found.
 *
 * @param candidates the list of candidate prices to evaluate
 * @return an Optional containing the most applicable price, or empty if no candidates are provided
 */
public class PriceSelectionPolicy {

    private static final Comparator<ProductPrice> PRIORITY_RULE = Comparator.comparing(ProductPrice::priority)
            .thenComparing(ProductPrice::startDate);

    public Optional<ProductPrice> selectApplicable(List<ProductPrice> candidates) {
        if (candidates == null || candidates.isEmpty()) {
            return Optional.empty();
        }

        return candidates.stream().max(PRIORITY_RULE);
    }
}
