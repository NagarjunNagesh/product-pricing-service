package com.example.productpricingservice.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;
import com.example.productpricingservice.application.port.out.ProductPriceRepository;
import com.example.productpricingservice.application.usecase.GetApplicablePriceService;

@Configuration
public class BeanConfiguration {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(
            ProductPriceRepository productPriceRepository) {
        return new GetApplicablePriceService(productPriceRepository);
    }
}
