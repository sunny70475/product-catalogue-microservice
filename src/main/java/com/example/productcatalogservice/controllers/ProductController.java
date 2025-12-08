package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.mapper.ProductMapper;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final IProductService productService;
    private final ProductMapper productMapper;

    // ---------------------- GET ALL PRODUCTS ----------------------
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        return ResponseEntity.ok(
                products.stream()
                        .map(productMapper::toDto)
                        .toList()
        );
    }

    // ---------------------- GET PRODUCT BY ID ----------------------
    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        if (id <= 0) {
            throw new IllegalArgumentException("Product ID must be positive");
        }

        Product product = productService.getProductById(id);

        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(product));
    }

    // ---------------------- CREATE PRODUCT ----------------------
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @RequestBody @Validated ProductDto productDto) {

        Product created = productService.createProduct(productMapper.toEntity(productDto));
        return ResponseEntity.status(201).body(productMapper.toDto(created));
    }

    // ---------------------- REPLACE PRODUCT ----------------------
    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> replaceProduct(
            @PathVariable Long id,
            @RequestBody @Validated ProductDto productDto) {

        Product updated = productService.replaceProduct(productMapper.toEntity(productDto), id);

        if (updated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(productMapper.toDto(updated));
    }

    // ---------------------- BULK CREATE ----------------------
    @PostMapping("/bulk")
    public ResponseEntity<List<ProductDto>> createMultipleProducts(
            @RequestBody List<ProductDto> productDtos) {

        List<Product> created = productService.createMultipleProducts(
                productDtos.stream()
                        .map(productMapper::toEntity)
                        .toList()
        );

        return ResponseEntity.status(201)
                .body(created.stream()
                        .map(productMapper::toDto)
                        .toList());
    }
}
