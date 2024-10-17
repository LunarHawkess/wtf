package com.maratsan.shop.mapper;

import com.maratsan.shop.entity.Review;
import com.maratsan.shop.request.ReviewRequest;
import com.maratsan.shop.response.ReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface ReviewMapper {

    ReviewResponse toResponse(Review review);

    default List<ReviewResponse> toResponse(List<Review> reviews) {
        return reviews.stream()
                .map(this::toResponse)
                .toList();
    }

    default Review fromRequest(ReviewRequest reviewRequest) {
        return fromRequest(reviewRequest, new Review());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    Review fromRequest(ReviewRequest reviewRequest, @MappingTarget Review review);

}
