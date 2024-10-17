package com.maratsan.shop.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Request object for category")
public class CategoryRequest {

    @Schema(description = "Name of the category", example = "Electronics")
    @NotBlank
    @Length(max = 64)
    private String name;

}
