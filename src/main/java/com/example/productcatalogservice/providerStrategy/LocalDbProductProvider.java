package com.example.productcatalogservice.providerStrategy;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import java.util.List;

@Component("localDbProvider")
@RequiredArgsConstructor
public class LocalDbProductProvider implements ProductProvider {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // -------------------------------------------------------
    // GET BY ID (local DB is source of truth)
    // -------------------------------------------------------
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // -------------------------------------------------------
    // CREATE PRODUCT
    // -------------------------------------------------------
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // -------------------------------------------------------
    // REPLACE PRODUCT
    // -------------------------------------------------------
    @Override
    public Product replaceProduct(Product input, Long id) {
        return productRepository.findById(id)
                .map(existing -> {
                    existing.setName(input.getName());
                    existing.setDescription(input.getDescription());
                    existing.setPrice(input.getPrice());
                    existing.setImageUrl(input.getImageUrl());

                    if (input.getCategory() != null) {
                        existing.setCategory(input.getCategory());
                    }

                    return productRepository.save(existing);
                })
                .orElse(null);
    }

    // -------------------------------------------------------
    // GET ALL PRODUCTS
    // -------------------------------------------------------
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // -------------------------------------------------------
    // BULK CREATE PRODUCTS
    // -------------------------------------------------------
    @Override
    public List<Product> createMultipleProducts(List<Product> products) {
        return productRepository.saveAll(products);
    }


    // Optional mapper methods (not really required for DB provider)
    private Product fromDto(FakeStoreProductDto dto) {
        Product product = modelMapper.map(dto, Product.class);

        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setName(dto.getCategory());
            product.setCategory(category);
        }
        return product;
    }

    private FakeStoreProductDto toDto(Product product) {
        FakeStoreProductDto dto = modelMapper.map(product, FakeStoreProductDto.class);

        if (product.getCategory() != null) {
            dto.setCategory(product.getCategory().getName());
        }
        return dto;
    }
}
