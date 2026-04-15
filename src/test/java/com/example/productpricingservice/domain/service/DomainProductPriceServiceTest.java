package com.example.productpricingservice.domain.service;

import com.example.productpricingservice.domain.exception.PriceNotFoundException;
import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.domain.port.ProductPriceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DomainProductPriceServiceTest {

    private ProductPriceRepository repository;
    private PriceSelectionPolicy policy;
    private DomainProductPriceService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductPriceRepository.class);
        policy = mock(PriceSelectionPolicy.class);
        service = new DomainProductPriceService(repository, policy);
    }

    @Test
    void shouldReturnSelectedPriceWhenRepositoryHasCandidates() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        ProductPrice expected = price(
                productId, brandId, 1L,
                LocalDateTime.of(2020, 6, 14, 10, 0),
                LocalDateTime.of(2020, 6, 14, 18, 30),
                1, "35.50"
        );

        List<ProductPrice> candidates = List.of(expected);

        when(repository.findApplicablePrices(startDate, productId, brandId)).thenReturn(candidates);
        when(policy.selectApplicable(candidates)).thenReturn(Optional.of(expected));

        ProductPrice actual = service.getApplicablePrice(startDate, productId, brandId);

        assertEquals(expected, actual);
        verify(repository).findApplicablePrices(startDate, productId, brandId);
        verify(policy).selectApplicable(candidates);
    }

    @Test
    void shouldThrowWhenNoPriceFound() {
        LocalDateTime startDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        List<ProductPrice> empty = List.of();

        when(repository.findApplicablePrices(startDate, productId, brandId)).thenReturn(empty);
        when(policy.selectApplicable(empty)).thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class,
                () -> service.getApplicablePrice(startDate, productId, brandId));
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
