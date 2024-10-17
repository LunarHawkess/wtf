package com.maratsan.shop.mapper;

import com.maratsan.shop.entity.Category;
import com.maratsan.shop.request.CategoryRequest;
import com.maratsan.shop.response.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    default List<CategoryResponse> toResponse(List<Category> categories) {
        return categories.stream()
                .map(this::toResponse)
                .toList();
    }

    default Category fromRequest(CategoryRequest categoryRequest) {
        return fromRequest(categoryRequest, new Category());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Category fromRequest(CategoryRequest categoryRequest, @MappingTarget Category category);

    default Category fromId(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

}
