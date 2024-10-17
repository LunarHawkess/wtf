package com.maratsan.shop.controller;

import com.maratsan.shop.request.ProductRequest;
import com.maratsan.shop.response.ErrorResponse;
import com.maratsan.shop.response.ProductResponse;
import com.maratsan.shop.response.ValidationErrorResponse;
import com.maratsan.shop.service.ProductService;
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

import java.util.List;

@Tag(name = "Products")
@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final static String PRODUCT_NOT_FOUND_EXAMPLE = """
            {
                "errorCode": "PRODUCT_NOT_FOUND",
                "description": "Product not found"
            }
            """;


    private final ProductService productService;


    @Operation(summary = "Get products")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponse> getProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Create product")
    @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                    schema = @Schema(
                            implementation = ValidationErrorResponse.class
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @Operation(summary = "Get product")
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = PRODUCT_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "Update product")
    @ApiResponse(
            responseCode = "400",
            description = "Validation error",
            content = @Content(
                    schema = @Schema(
                            implementation = ValidationErrorResponse.class
                    )
            )
    )
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = PRODUCT_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{productId}")
    public ProductResponse updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productId, productRequest);
    }

    @Operation(summary = "Delete product")
    @ApiResponse(
            responseCode = "404",
            description = "Product not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = PRODUCT_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        productService.deleteProductById(productId);
    }

}
