package com.example.productpricingservice.infrastructure.adapters.in.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.test.web.servlet.MockMvc;

import com.example.productpricingservice.application.port.in.GetApplicablePriceUseCase;

@SpringBootTest
@AutoConfigureMockMvc
@Import(GlobalExceptionHandlerUnexpectedIT.FailingUseCaseConfig.class)
class GlobalExceptionHandlerUnexpectedIT {

    private static final String PRICES_PATH = "/api/prices";
    private static final String CODE_JSON_PATH = "$.code";
    private static final String MESSAGE_JSON_PATH = "$.message";
    private static final String INTERNAL_SERVER_ERROR_CODE = "INTERNAL_SERVER_ERROR";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnInternalServerErrorForUnexpectedException() throws Exception {
        mockMvc.perform(get(PRICES_PATH)
                .param("applicationDateTime", "2020-06-14T10:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath(CODE_JSON_PATH).value(INTERNAL_SERVER_ERROR_CODE))
                .andExpect(jsonPath(MESSAGE_JSON_PATH).value("Unexpected server error"));
    }

    @TestConfiguration
    static class FailingUseCaseConfig {

        @Bean
        @Primary
        GetApplicablePriceUseCase failingGetApplicablePriceUseCase() {
            return (applicationDateTime, productId, brandId) -> {
                throw new RuntimeException("Forced exception to validate global error handling");
            };
        }
    }
}
