package com.maratsan.shop.mapper;

import com.maratsan.shop.entity.Product;
import com.maratsan.shop.request.ProductRequest;
import com.maratsan.shop.response.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = CategoryMapper.class)
public interface ProductMapper {

    ProductResponse toResponse(Product product);

    default List<ProductResponse> toResponse(List<Product> products) {
        return products.stream()
                .map(this::toResponse)
                .toList();
    }


    default Product fromRequest(ProductRequest productRequest) {
        return fromRequest(productRequest, new Product());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Product fromRequest(ProductRequest productRequest, @MappingTarget Product product);

}
