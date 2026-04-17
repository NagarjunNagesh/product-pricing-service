package com.example.productpricingservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;
import com.example.productpricingservice.application.port.out.ProductPriceRepository;
import com.example.productpricingservice.application.usecase.GetApplicablePriceService;
import com.example.productpricingservice.domain.service.PriceSelectionPolicy;

@Configuration
public class BeanConfiguration {

    @Bean
    public PriceSelectionPolicy priceSelectionPolicy() {
        return new PriceSelectionPolicy();
    }

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(
            ProductPriceRepository productPriceRepository,
            PriceSelectionPolicy priceSelectionPolicy) {
        return new GetApplicablePriceService(productPriceRepository, priceSelectionPolicy);
    }
}
