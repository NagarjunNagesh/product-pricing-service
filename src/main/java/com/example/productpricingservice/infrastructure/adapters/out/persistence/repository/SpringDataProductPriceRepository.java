package com.example.productpricingservice.infrastructure.adapters.out.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.productpricingservice.infrastructure.adapters.out.persistence.entity.ProductPriceEntity;

public interface SpringDataProductPriceRepository extends JpaRepository<ProductPriceEntity, Long> {

  /*
   * Select prices where brand = ?brandId, product = ?productId, startDate <=
   * ?startDate and endDate >= ?startDate, ordered by priority desc and startDate
   * desc
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
      LocalDateTime endDate,
      Pageable pageable);

  default Optional<ProductPriceEntity> findTopApplicablePrice(
      Long brandId,
      Long productId,
      LocalDateTime applicationDateTime) {
    return fetchProductPrice(
        brandId,
        productId,
        applicationDateTime,
        applicationDateTime,
        PageRequest.of(0, 1)).stream().findFirst();
  }
}
