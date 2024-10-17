package com.maratsan.shop.service;

import com.maratsan.shop.exception.NotFoundException;
import com.maratsan.shop.mapper.ReviewMapper;
import com.maratsan.shop.repository.ReviewRepository;
import com.maratsan.shop.request.ReviewRequest;
import com.maratsan.shop.response.ReviewResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final ProductService productService;


    public List<ReviewResponse> getAllReviewsByProductId(Long productId) {
        var product = productService.getById(productId);
        var reviews = reviewRepository.findAllByProduct(product);

        return reviewMapper.toResponse(reviews);
    }

    @Transactional
    public ReviewResponse createReviewByProductId(Long productId, @Valid ReviewRequest reviewRequest) {
        var product = productService.getById(productId);

        var review = reviewMapper.fromRequest(reviewRequest);
        review.setProduct(product);

        review = reviewRepository.save(review);

        return reviewMapper.toResponse(review);
    }

    @Transactional
    public void deleteReviewById(@NotNull @Min(1) Long id) {
        reviewRepository.findById(id)
                .ifPresentOrElse(reviewRepository::delete, () -> {
                    throw new NotFoundException(ErrorCode.REVIEW_NOT_FOUND.description, ErrorCode.REVIEW_NOT_FOUND);
                });
    }


    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        REVIEW_NOT_FOUND("Review not found");

        private final String description;

    }

}
