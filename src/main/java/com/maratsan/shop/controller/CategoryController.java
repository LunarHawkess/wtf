package com.maratsan.shop.controller;

import com.maratsan.shop.request.CategoryRequest;
import com.maratsan.shop.response.ErrorResponse;
import com.maratsan.shop.response.CategoryResponse;
import com.maratsan.shop.response.ValidationErrorResponse;
import com.maratsan.shop.service.CategoryService;
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

@Tag(name = "Categories")
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final static String CATEGORY_NOT_FOUND_EXAMPLE = """
            {
                "errorCode": "CATEGORY_NOT_FOUND",
                "description": "Category not found"
            }
            """;

    private final static String CATEGORY_NAME_ALREADY_EXISTS_EXAMPLE = """
            {
                "errorCode": "CATEGORY_NAME_ALREADY_EXISTS",
                "description": "Category with this name already exists"
            }
            """;


    private final CategoryService categoryService;


    @Operation(summary = "Get categories")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<CategoryResponse> getCategories() {
        return categoryService.getAllCategories();
    }

    @Operation(summary = "Create category")
    @ApiResponse(
            responseCode = "400",
            description = "Category with this name already exists",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = CATEGORY_NAME_ALREADY_EXISTS_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public CategoryResponse createCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.createCategory(categoryRequest);
    }

    @Operation(summary = "Get category")
    @ApiResponse(
            responseCode = "404",
            description = "Category not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = CATEGORY_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{categoryId}")
    public CategoryResponse getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategoryById(categoryId);
    }

    @Operation(summary = "Update category")
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
            description = "Category not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = CATEGORY_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{categoryId}")
    public CategoryResponse updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        return categoryService.updateCategory(categoryId, categoryRequest);
    }

    @Operation(summary = "Delete category")
    @ApiResponse(
            responseCode = "404",
            description = "Category not found",
            content = @Content(
                    schema = @Schema(
                            implementation = ErrorResponse.class,
                            example = CATEGORY_NOT_FOUND_EXAMPLE
                    )
            )
    )
    @SecurityRequirement(name = "basicScheme")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategoryById(categoryId);
    }

}
