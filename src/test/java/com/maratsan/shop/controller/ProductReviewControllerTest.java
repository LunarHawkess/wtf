package com.maratsan.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maratsan.shop.common.Rating;
import com.maratsan.shop.request.ReviewRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(value = {"/sql/product-review-controller-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/sql/product-review-controller-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class ProductReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void getProductReviewsTest() throws Exception {
        this.mockMvc.perform(get("/products/1/reviews")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].comment", is("Review 1")))
                .andExpect(jsonPath("$[0].rating", is("BAD")))
                .andExpect(jsonPath("$[1].comment", is("Review 2")))
                .andExpect(jsonPath("$[1].rating", is("GOOD")));
    }

    @Test
    void createProductReviewByAnonymousTest() throws Exception {
        var reviewRequest = new ReviewRequest("Review 3", Rating.GOOD);

        this.mockMvc.perform(post("/categories")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("FORBIDDEN")));
    }

    @Test
    @WithUserDetails
    void createProductReviewByUserTest() throws Exception {
        var reviewRequest = new ReviewRequest("Review 3", Rating.GOOD);

        this.mockMvc.perform(post("/products/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.comment", is("Review 3")))
                .andExpect(jsonPath("$.rating", is("GOOD")));
    }

    @Test
    @WithUserDetails
    void createProductReviewByUserWithValidationViolationsTest() throws Exception {
        var reviewRequest = new ReviewRequest("", Rating.GOOD);

        this.mockMvc.perform(post("/products/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("VALIDATION_ERROR")));
    }

    @Test
    @WithUserDetails
    void createProductReviewByUserProductNotFoundTest() throws Exception {
        var reviewRequest = new ReviewRequest("Review 3", Rating.GOOD);

        this.mockMvc.perform(post("/products/10/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(reviewRequest)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.errorCode", is("PRODUCT_NOT_FOUND")));
    }

}
