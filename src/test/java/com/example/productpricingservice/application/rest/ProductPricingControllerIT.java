package com.example.productpricingservice.application.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
class ProductPricingControllerIT {

    static MockMvc mockMvc;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("startDate", "not-a-date")
                .param("productId", "1")
                .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("INVALID_REQUEST"));
    }

    @Test
    void shouldReturnNotFoundWhenPriceMissing() throws Exception {
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-14T10:00")
                .param("productId", "1")
                .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRICE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void shouldReturnExpectedPricesForRequirementsScenarios() throws Exception {
        // Test 1: 2020-06-14 10:00 -> priceList 1, price 35.50
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-14T10:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 2: 2020-06-14 16:00 -> priceList 2, price 25.45
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-14T16:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(2))
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 3: 2020-06-14 21:00 -> priceList 1, price 35.50
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-14T21:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 4: 2020-06-15 10:00 -> priceList 3, price 30.50
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-15T10:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(3))
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 5: 2020-06-16 21:00 -> priceList 4, price 38.95
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-16T21:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(4))
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 6: 2020-06-16 15:00 -> priceList 4, price 38.95
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-15T15:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceList").value(1))
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.currency").value("EUR"));

        // Test 7: 2020-06-13 10:00 -> priceList 1, price 35.50
        mockMvc.perform(get("/api/prices")
                .param("startDate", "2020-06-13T10:00")
                .param("productId", "35455")
                .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("PRICE_NOT_FOUND"))
                .andExpect(jsonPath("$.message").exists());

    }
}
