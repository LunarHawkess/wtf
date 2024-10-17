package com.maratsan.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Response object for validation errors")
public class ValidationErrorResponse {

    @Schema(description = "Error code", example = "VALIDATION_ERROR")
    private String errorCode;

    @Schema(description = "Error message", example = "name must not be null")
    private String description;

    @Schema(description = "Violations")
    private List<Violation> violations;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Schema(description = "Response object for validation violations")
    public static class Violation {

        @Schema(description = "Field name", example = "name")
        private String fieldName;

        @Schema(description = "Message", example = "must not be null")
        private String message;

    }

}
