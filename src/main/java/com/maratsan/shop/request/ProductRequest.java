package com.maratsan.shop.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Request object for product")
public class ProductRequest {

    @Schema(description = "Name of the product", example = "iPhone 16")
    @NotBlank
    @Length(max = 64)
    private String name;

    @Schema(description = "Description of the product", example = "The latest iPhone model")
    private String description;

    @Schema(description = "Price of the product", example = "999.99")
    @NotNull
    @Min(0)
    private BigDecimal price;

    @Schema(description = "List of categories of the product", example = "[1, 2]")
    @NotNull
    private List<Long> categories;

}
