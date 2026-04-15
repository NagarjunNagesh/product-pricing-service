package com.example.productpricingservice.infrastructure.adapter.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.domain.port.ProductPriceRepository;
import com.example.productpricingservice.infrastructure.adapter.persistence.entity.ProductPriceEntity;
import com.example.productpricingservice.infrastructure.adapter.persistence.repository.SpringDataProductPriceRepository;

@Component
public class ProductPriceRepositoryAdapter implements ProductPriceRepository {

    private final SpringDataProductPriceRepository springDataProductPriceRepository;

    public ProductPriceRepositoryAdapter(SpringDataProductPriceRepository springDataProductPriceRepository) {
        this.springDataProductPriceRepository = springDataProductPriceRepository;
    }

    @Override
    public List<ProductPrice> findApplicablePrices(LocalDateTime startDate, Long productId, Long brandId) {
        return springDataProductPriceRepository
                .fetchProductPrice(
                        brandId, productId, startDate, startDate)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private ProductPrice toDomain(ProductPriceEntity entity) {
        return ProductPrice.builder()
                .productId(entity.getProductId())
                .brandId(entity.getBrandId())
                .priceList(entity.getPriceList())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .build();
    }
}
