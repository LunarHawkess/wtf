package com.maratsan.shop.response;

import com.maratsan.shop.common.Rating;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Response object for review")
public class ReviewResponse {

    @Schema(description = "Unique identifier of the review", example = "1")
    private Long id;

    @Schema(description = "Comment of the review", example = "Great product!")
    private String comment;

    @Schema(description = "Rating of the review", example = "GOOD")
    private Rating rating;

}
