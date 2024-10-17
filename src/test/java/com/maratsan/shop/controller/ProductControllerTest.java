package com.maratsan.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maratsan.shop.request.ProductRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/product-controller-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/product-controller-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getAllProductsTest() throws Exception {
        mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Product 1")))
                .andExpect(jsonPath("$[1].name", is("Product 2")))
                .andExpect(jsonPath("$[2].name", is("Product 3")));
    }

    @Test
    void createProductByAnonymousTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails
    void createProductByUserTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails("admin")
    void createProductByAdminTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Product 4")))
                .andExpect(jsonPath("$.description", is("Description 4")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andExpect(jsonPath("$.categories[0].id", is(1)))
                .andExpect(jsonPath("$.categories[1].id", is(2)));
    }

    @Test
    @WithUserDetails("admin")
    void createProductByAdminWithValidationViolationsTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("-99.99"), List.of(1L, 2L));

        this.mockMvc.perform(post("/products")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")));
    }

    @Test
    void getProductByIdTest() throws Exception {
        mockMvc.perform(get("/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 1")))
                .andExpect(jsonPath("$.description", is("Description 1")))
                .andExpect(jsonPath("$.price", is(100.0)))
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andExpect(jsonPath("$.categories[0].id", is(1)))
                .andExpect(jsonPath("$.categories[1].id", is(2)));
    }

    @Test
    void getProductByIdNotFoundTest() throws Exception {
        mockMvc.perform(get("/products/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("PRODUCT_NOT_FOUND")));
    }

    @Test
    void updateProductByAnonymousTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(put("/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails
    void updateProductByUserTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(put("/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails("admin")
    void updateProductByAdminTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("99.99"), List.of(1L, 2L));

        this.mockMvc.perform(put("/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Product 4")))
                .andExpect(jsonPath("$.description", is("Description 4")))
                .andExpect(jsonPath("$.price", is(99.99)))
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andExpect(jsonPath("$.categories[0].id", is(1)))
                .andExpect(jsonPath("$.categories[1].id", is(2)));
    }

    @Test
    @WithUserDetails("admin")
    void updateProductByAdminWithValidationViolationsTest() throws Exception {
        var productRequest = new ProductRequest("Product 4", "Description 4", new BigDecimal("-99.99"), List.of(1L, 2L));

        this.mockMvc.perform(put("/products/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(productRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")));
    }

    @Test
    void deleteProductByAnonymousTest() throws Exception {
        this.mockMvc.perform(delete("/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails
    void deleteProductByUserTest() throws Exception {
        this.mockMvc.perform(delete("/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails("admin")
    void deleteProductByAdminTest() throws Exception {
        this.mockMvc.perform(delete("/products/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("admin")
    void deleteProductByAdminNotFoundTest() throws Exception {
        this.mockMvc.perform(delete("/products/100")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("PRODUCT_NOT_FOUND")));
    }

}
