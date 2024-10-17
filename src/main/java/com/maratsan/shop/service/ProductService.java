package com.maratsan.shop.service;

import com.maratsan.shop.entity.Product;
import com.maratsan.shop.exception.NotFoundException;
import com.maratsan.shop.mapper.ProductMapper;
import com.maratsan.shop.repository.ProductRepository;
import com.maratsan.shop.request.ProductRequest;
import com.maratsan.shop.response.ProductResponse;
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
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public List<ProductResponse> getAllProducts() {
        var products = productRepository.findAll();

        return productMapper.toResponse(products);
    }

    public ProductResponse getProductById(@NotNull @Min(1) Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND.description, ErrorCode.PRODUCT_NOT_FOUND));

        return productMapper.toResponse(product);
    }

    public Product getById(@NotNull @Min(1) Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND.description, ErrorCode.PRODUCT_NOT_FOUND));
    }

    @Transactional
    public ProductResponse createProduct(@NotNull @Valid ProductRequest productRequest) {
        var product = productMapper.fromRequest(productRequest);

        product = productRepository.save(product);

        return productMapper.toResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(@NotNull @Min(1) Long id, @NotNull @Valid ProductRequest productRequest) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND.description, ErrorCode.PRODUCT_NOT_FOUND));

        product = productMapper.fromRequest(productRequest, product);

        return productMapper.toResponse(product);
    }

    @Transactional
    public void deleteProductById(@NotNull @Min(1) Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete, () -> {
                    throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND.description, ErrorCode.PRODUCT_NOT_FOUND);
                });
    }

    @Getter
    @RequiredArgsConstructor
    public enum ErrorCode {
        PRODUCT_NOT_FOUND("Product not found");

        private final String description;

    }

}
