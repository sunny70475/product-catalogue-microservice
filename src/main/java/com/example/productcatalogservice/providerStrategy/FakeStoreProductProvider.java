package com.example.productcatalogservice.providerStrategy;

import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("fakeStoreProvider")
@RequiredArgsConstructor
public class FakeStoreProductProvider implements ProductProvider {

    private final FakeStoreApiClient fakeStoreApiClient;
    private final ModelMapper modelMapper;

    // -------------------------------------------------------
    // GET BY ID
    // -------------------------------------------------------
    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto dto = fakeStoreApiClient.getFakeStoreProductById(id);
        return dto != null ? from(dto) : null;
    }

    // -------------------------------------------------------
    // CREATE (creates inside FakeStore API)
    // -------------------------------------------------------
    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto dtoInput = from(product);
        FakeStoreProductDto dtoOutput = fakeStoreApiClient.createFakeStoreProduct(dtoInput);
        return dtoOutput != null ? from(dtoOutput) : null;
    }

    // -------------------------------------------------------
    // REPLACE
    // -------------------------------------------------------
    @Override
    public Product replaceProduct(Product input, Long id) {
        FakeStoreProductDto dtoInput = from(input);
        FakeStoreProductDto dtoOutput = fakeStoreApiClient.replaceFakeStoreProduct(dtoInput, id);
        return dtoOutput != null ? from(dtoOutput) : null;
    }

    // -------------------------------------------------------
    // GET ALL
    // -------------------------------------------------------
    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> dtos = fakeStoreApiClient.getAllProducts();
        List<Product> result = new ArrayList<>();

        for (FakeStoreProductDto dto : dtos) {
            result.add(from(dto));
        }

        return result;
    }

    // -------------------------------------------------------
    // BULK CREATE
    // -------------------------------------------------------
    @Override
    public List<Product> createMultipleProducts(List<Product> products) {
        List<Product> created = new ArrayList<>();
        for (Product p : products) {
            Product out = createProduct(p);
            if (out != null) created.add(out);
        }
        return created;
    }

    // -------------------------------------------------------
    // MAPPERS
    // -------------------------------------------------------
    private Product from(FakeStoreProductDto dto) {

        // Basic mapping
        Product product = modelMapper.map(dto, Product.class);

        // Fix Category (since dto has string category name)
        if (dto.getCategory() != null) {
            Category category = new Category();
            category.setName(dto.getCategory());
            product.setCategory(category);
        }

        return product;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto dto = modelMapper.map(product, FakeStoreProductDto.class);

        if (product.getCategory() != null) {
            dto.setCategory(product.getCategory().getName());
        }

        return dto;
    }
}
