package com.maratsan.shop.controller;

import com.maratsan.shop.request.ReviewRequest;
import com.maratsan.shop.response.ErrorResponse;
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

@Tag(name = "Reviews")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final static String REVIEW_NOT_FOUND_EXAMPLE = """
            {
                "errorCode": "REVIEW_NOT_FOUND",
                "description": "Review not found"
            }
            """;


    private final ReviewService reviewService;


    @Operation(summary = "Delete review")
    @ApiResponse(
            responseCode = "404",
            description = "Review not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = REVIEW_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReviewById(reviewId);
    }

}
