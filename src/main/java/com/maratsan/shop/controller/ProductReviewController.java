package com.maratsan.shop.controller;

import com.maratsan.shop.request.ReviewRequest;
import com.maratsan.shop.response.ReviewResponse;
import com.maratsan.shop.response.ValidationErrorResponse;
import com.maratsan.shop.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product Reviews")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products/{productId}/reviews")
public class ProductReviewController {

    private final ReviewService reviewService;


    @Operation(summary = "Get product reviews")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ReviewResponse> getProductReviews(@PathVariable Long productId) {
        return reviewService.getAllReviewsByProductId(productId);
    }

    @Operation(summary = "Create product review")
    @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                    schema = @Schema(
                            implementation = ValidationErrorResponse.class
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ReviewResponse createProductReview(@PathVariable Long productId, @RequestBody ReviewRequest reviewRequest) {
        return reviewService.createReviewByProductId(productId, reviewRequest);
    }

}
