package com.maratsan.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Response object for product")
public class ProductResponse {

    @Schema(description = "Unique identifier of the product", example = "1")
    private Long id;

    @Schema(description = "Name of the product", example = "iPhone 16")
    private String name;

    @Schema(description = "Description of the product", example = "The latest iPhone model")
    private String description;

    @Schema(description = "Price of the product", example = "999.99")
    private BigDecimal price;

    @Schema(description = "List of categories of the product")
    private List<CategoryResponse> categories;

    @Schema(description = "Average rating of the product", example = "4")
    private Integer averageRating;

}
