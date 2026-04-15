package com.example.productpricingservice.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.productpricingservice.domain.model.ProductPrice;

class PriceSelectionPolicyTest {

        private final PriceSelectionPolicy policy = new PriceSelectionPolicy();

        @Test
        void shouldReturnCandidateWithHighestPriority() {
                ProductPrice lowPriority = price(
                                35455L, 1L, 1L,
                                LocalDateTime.of(2020, 6, 14, 10, 0),
                                LocalDateTime.of(2020, 6, 14, 18, 30),
                                0, "35.50");

                ProductPrice highPriority = price(
                                35455L, 1L, 2L,
                                LocalDateTime.of(2020, 6, 14, 15, 0),
                                LocalDateTime.of(2020, 6, 14, 18, 30),
                                1, "25.45");

                Optional<ProductPrice> selected = policy.selectApplicable(List.of(lowPriority, highPriority));

                assertTrue(selected.isPresent());
                assertEquals(highPriority, selected.get());
        }

        @Test
        void shouldReturnLaterStartDateWhenPrioritiesAreEqual() {
                ProductPrice earlierStart = price(
                                35455L, 1L, 3L,
                                LocalDateTime.of(2020, 6, 15, 0, 0),
                                LocalDateTime.of(2020, 6, 15, 11, 0),
                                1, "30.50");

                ProductPrice laterStart = price(
                                35455L, 1L, 4L,
                                LocalDateTime.of(2020, 6, 15, 16, 0),
                                LocalDateTime.of(2020, 12, 31, 23, 59),
                                1, "38.95");

                Optional<ProductPrice> selected = policy.selectApplicable(List.of(earlierStart, laterStart));

                assertTrue(selected.isPresent());
                assertEquals(laterStart, selected.get());
        }

        @Test
        void shouldReturnEmptyWhenCandidateListIsEmpty() {
                Optional<ProductPrice> selected = policy.selectApplicable(List.of());

                assertTrue(selected.isEmpty());
        }

        private ProductPrice price(
                        Long productId,
                        Long brandId,
                        Long priceList,
                        LocalDateTime startDate,
                        LocalDateTime endDate,
                        Integer priority,
                        String price) {
                return ProductPrice.builder().productId(productId)
                                .brandId(brandId)
                                .priceList(priceList)
                                .startDate(startDate)
                                .endDate(endDate)
                                .priority(priority)
                                .price(new BigDecimal(price))
                                .currency("EUR")
                                .build();
        }
}
