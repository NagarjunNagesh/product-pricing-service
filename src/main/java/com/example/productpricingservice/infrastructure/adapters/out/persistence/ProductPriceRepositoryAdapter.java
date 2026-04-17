package com.example.productpricingservice.infrastructure.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.productpricingservice.application.port.out.ProductPriceRepository;
import com.example.productpricingservice.domain.model.ProductPrice;
import com.example.productpricingservice.infrastructure.adapters.out.persistence.entity.ProductPriceEntity;
import com.example.productpricingservice.infrastructure.adapters.out.persistence.repository.SpringDataProductPriceRepository;

@Component
public class ProductPriceRepositoryAdapter implements ProductPriceRepository {
    private static final Logger log = LoggerFactory.getLogger(ProductPriceRepositoryAdapter.class);

    private final SpringDataProductPriceRepository springDataProductPriceRepository;

    public ProductPriceRepositoryAdapter(SpringDataProductPriceRepository springDataProductPriceRepository) {
        this.springDataProductPriceRepository = springDataProductPriceRepository;
    }

    @Override
    public List<ProductPrice> findApplicablePrices(LocalDateTime applicationDateTime, Long productId, Long brandId) {
        return springDataProductPriceRepository
                .fetchProductPrice(
                        brandId, productId, applicationDateTime, applicationDateTime)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    private ProductPrice toDomain(ProductPriceEntity entity) {
        log.debug("Mapping ProductPriceEntity {} to domain ProductPrice", entity);
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
