package com.example.productpricingservice.infrastructure.adapters.in.web;

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

        private static final String PRICES_PATH = "/api/prices";
        private static final String APPLICATION_DATE_TIME_PARAM = "applicationDateTime";
        private static final String PRODUCT_ID_PARAM = "productId";
        private static final String BRAND_ID_PARAM = "brandId";
        private static final String CODE_JSON_PATH = "$.code";
        private static final String MESSAGE_JSON_PATH = "$.message";
        private static final String PRICE_LIST_JSON_PATH = "$.priceList";
        private static final String PRICE_JSON_PATH = "$.price";
        private static final String CURRENCY_JSON_PATH = "$.currency";
        private static final String BRAND_ID_1 = "1";
        private static final String PRODUCT_ID_35455 = "35455";
        private static final String CURRENCY_EUR = "EUR";
        private static final String INVALID_REQUEST = "INVALID_REQUEST";
        private static final String PRICE_NOT_FOUND = "PRICE_NOT_FOUND";

        static MockMvc mockMvc;

        @BeforeAll
        static void beforeAll(@Autowired WebApplicationContext wac) {
                mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        }

        @Test
        void shouldReturnBadRequestForInvalidDateFormat() throws Exception {
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "not-a-date")
                                .param(PRODUCT_ID_PARAM, BRAND_ID_1)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath(CODE_JSON_PATH).value(INVALID_REQUEST));
        }

        @Test
        void shouldReturnBadRequestForNegativeProductId() throws Exception {
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-14T10:00")
                                .param(PRODUCT_ID_PARAM, "-1")
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isBadRequest())
                                .andExpect(jsonPath(CODE_JSON_PATH).value(INVALID_REQUEST));
        }

        @Test
        void shouldReturnNotFoundWhenPriceMissing() throws Exception {
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-14T10:00")
                                .param(PRODUCT_ID_PARAM, BRAND_ID_1)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath(CODE_JSON_PATH).value(PRICE_NOT_FOUND))
                                .andExpect(jsonPath(MESSAGE_JSON_PATH).exists());
        }

        @Test
        void shouldReturnExpectedPricesForRequirementsScenarios() throws Exception {
                // Test 1: 2020-06-14 10:00 -> priceList 1, price 35.50
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-14T10:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(1))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(35.50))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 2: 2020-06-14 16:00 -> priceList 2, price 25.45
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-14T16:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(2))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(25.45))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 3: 2020-06-14 21:00 -> priceList 1, price 35.50
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-14T21:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(1))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(35.50))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 4: 2020-06-15 10:00 -> priceList 3, price 30.50
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-15T10:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(3))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(30.50))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 5: 2020-06-16 21:00 -> priceList 4, price 38.95
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-16T21:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(4))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(38.95))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 6: 2020-06-16 15:00 -> priceList 4, price 38.95
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-15T15:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(PRICE_LIST_JSON_PATH).value(1))
                                .andExpect(jsonPath(PRICE_JSON_PATH).value(35.50))
                                .andExpect(jsonPath(CURRENCY_JSON_PATH).value(CURRENCY_EUR));

                // Test 7: 2020-06-13 10:00 -> priceList 1, price 35.50
                mockMvc.perform(get(PRICES_PATH)
                                .param(APPLICATION_DATE_TIME_PARAM, "2020-06-13T10:00")
                                .param(PRODUCT_ID_PARAM, PRODUCT_ID_35455)
                                .param(BRAND_ID_PARAM, BRAND_ID_1))
                                .andExpect(status().isNotFound())
                                .andExpect(jsonPath(CODE_JSON_PATH).value(PRICE_NOT_FOUND))
                                .andExpect(jsonPath(MESSAGE_JSON_PATH).exists());

        }
}
