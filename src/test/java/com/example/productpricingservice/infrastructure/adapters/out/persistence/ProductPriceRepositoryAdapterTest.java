package com.example.productpricingservice.infrastructure.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.infrastructure.adapters.out.persistence.entity.ProductPriceEntity;
import com.example.productpricingservice.infrastructure.adapters.out.persistence.repository.SpringDataProductPriceRepository;

class ProductPriceRepositoryAdapterTest {

    @Test
    void fetchesAndMapsEntities() {
        SpringDataProductPriceRepository repo = mock(SpringDataProductPriceRepository.class);

        LocalDateTime start = LocalDateTime.of(2020, 6, 14, 10, 0);

        ProductPriceEntity entity = new ProductPriceEntity();
        entity.setBrandId(1L);
        entity.setProductId(35455L);
        entity.setPriceList(1L);
        entity.setStartDate(start);
        entity.setEndDate(LocalDateTime.of(2020, 12, 31, 23, 59));
        entity.setPriority(0);
        entity.setPrice(new BigDecimal("35.50"));
        entity.setCurrency("EUR");

        when(repo.findTopApplicablePrice(1L, 35455L, start)).thenReturn(Optional.of(entity));

        ProductPriceRepositoryAdapter adapter = new ProductPriceRepositoryAdapter(repo);

        Optional<ProductPrice> result = adapter.findApplicablePrices(start, 35455L, 1L);

        assertTrue(result.isPresent());
        ProductPrice p = result.get();

        assertEquals(entity.getProductId(), p.productId());
        assertEquals(entity.getBrandId(), p.brandId());
        assertEquals(entity.getPriceList(), p.priceList());
        assertEquals(entity.getStartDate(), p.startDate());
        assertEquals(entity.getEndDate(), p.endDate());
        assertEquals(entity.getPriority(), p.priority());
        assertEquals(entity.getPrice(), p.price());
        assertEquals(entity.getCurrency(), p.currency());

        verify(repo).findTopApplicablePrice(1L, 35455L, start);
    }
}
