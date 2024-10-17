package com.maratsan.shop.request;

import com.maratsan.shop.common.Rating;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Request object for review")
public class ReviewRequest {

    @Schema(description = "Comment", example = "Great product!")
    @NotBlank
    @Length(max = 255)
    private String comment;

    @Schema(description = "Rating", example = "GOOD")
    @NotNull
    private Rating rating;

}
