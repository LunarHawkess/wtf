package com.maratsan.shop.service;

import com.maratsan.shop.exception.BusinessException;
import com.maratsan.shop.exception.NotFoundException;
import com.maratsan.shop.mapper.CategoryMapper;
import com.maratsan.shop.repository.CategoryRepository;
import com.maratsan.shop.request.CategoryRequest;
import com.maratsan.shop.response.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RequiredArgsConstructor
@Validated
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;


    public List<CategoryResponse> getAllCategories() {
        var categories = categoryRepository.findAll();

        return categoryMapper.toResponse(categories);
    }

    public CategoryResponse getCategoryById(@NotNull @Min(1) Long id) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.description, ErrorCode.CATEGORY_NOT_FOUND));

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse createCategory(@NotNull @Valid CategoryRequest categoryRequest) {
        var category = categoryMapper.fromRequest(categoryRequest);

        var exists = categoryRepository.existsByName(category.getName());
        if (exists) {
            throw new BusinessException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTS.description, ErrorCode.CATEGORY_NAME_ALREADY_EXISTS);
        }

        category = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(@NotNull @Min(1) Long id, @NotNull @Valid CategoryRequest categoryRequest) {
        var category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.description, ErrorCode.CATEGORY_NOT_FOUND));

        if (!categoryRequest.getName().equals(category.getName())) {
            var exists = categoryRepository.existsByName(categoryRequest.getName());
            if (exists) {
                throw new BusinessException(ErrorCode.CATEGORY_NAME_ALREADY_EXISTS.description, ErrorCode.CATEGORY_NAME_ALREADY_EXISTS);
            }
        }

        category = categoryMapper.fromRequest(categoryRequest, category);

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void deleteCategoryById(@NotNull @Min(1) Long id) {
        categoryRepository.findById(id)
                .ifPresentOrElse(categoryRepository::delete, () -> {
                    throw new NotFoundException(ErrorCode.CATEGORY_NOT_FOUND.description, ErrorCode.CATEGORY_NOT_FOUND);
                });
    }

    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        CATEGORY_NOT_FOUND("Category not found"),
        CATEGORY_NAME_ALREADY_EXISTS("Category with this name already exists");

        private final String description;

    }

}
