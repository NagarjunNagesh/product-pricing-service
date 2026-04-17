package com.example.productpricingservice.infrastructure.adapters.out.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.productpricingservice.infrastructure.adapters.out.persistence.entity.ProductPriceEntity;

public interface SpringDataProductPriceRepository extends JpaRepository<ProductPriceEntity, Long> {

  /*
    Select prices where brand = ?brandId, product = ?productId, startDate <=
     ?startDate and endDate >= ?startDate, ordered by priority desc and startDate
     desc
  */
  @Query("""
      SELECT p FROM ProductPriceEntity p
      WHERE p.brandId = :brandId
        AND p.productId = :productId
        AND p.startDate <= :startDate
        AND p.endDate >= :endDate
      ORDER BY p.priority DESC, p.startDate DESC
      """)
  List<ProductPriceEntity> fetchProductPrice(
      Long brandId,
      Long productId,
      LocalDateTime startDate,
      LocalDateTime endDate);
}
