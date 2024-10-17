package com.maratsan.shop.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Schema(description = "Response object for error")
public class ErrorResponse {

    @Schema(description = "Error code", example = "INTERNAL_SERVER_ERROR")
    private String errorCode;

    @Schema(description = "Error message", example = "Internal server error")
    private String description;

}
