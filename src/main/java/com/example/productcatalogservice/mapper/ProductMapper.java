package com.example.productcatalogservice.mapper;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product) {
        if (product == null) return null;

        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setImageUrl(product.getImageUrl());

        if (product.getCategory() != null) {
            CategoryDto c = new CategoryDto();
            c.setId(product.getCategory().getId());
            c.setName(product.getCategory().getName());
            c.setDescription(product.getCategory().getDescription());
            dto.setCategory(c);
        }

        return dto;
    }

    public Product toEntity(ProductDto dto) {
        if (dto == null) return null;

        Product p = new Product();
        p.setId(dto.getId());
        p.setName(dto.getName());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setImageUrl(dto.getImageUrl());

        if (dto.getCategory() != null) {
            Category c = new Category();
            c.setId(dto.getCategory().getId());
            c.setName(dto.getCategory().getName());
            c.setDescription(dto.getCategory().getDescription());
            p.setCategory(c);
        }

        return p;
    }
}
