package com.example.productpricingservice.infrastructure.config;

import com.example.productpricingservice.domain.port.ProductPriceRepository;
import com.example.productpricingservice.domain.service.DomainProductPriceService;
import com.example.productpricingservice.domain.service.PriceSelectionPolicy;
import com.example.productpricingservice.domain.service.ProductPriceService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceSelectionPolicy priceSelectionPolicy() {
        return new PriceSelectionPolicy();
    }

    @Bean
    public ProductPriceService productPriceService(
            ProductPriceRepository productPriceRepository,
            PriceSelectionPolicy priceSelectionPolicy) {
        return new DomainProductPriceService(productPriceRepository, priceSelectionPolicy);
    }
}
