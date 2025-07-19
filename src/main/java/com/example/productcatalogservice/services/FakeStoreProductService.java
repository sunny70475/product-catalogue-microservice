package com.example.productcatalogservice.services;


import com.example.productcatalogservice.clients.FakeStoreApiClient;
import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FakeStoreProductService implements IProductService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;    //RestTemplate object (internally builder pattern)

    @Autowired
    private FakeStoreApiClient fakeStoreApiClient;

    @Override
    public Product getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getFakeStoreProductById(id);
        if (fakeStoreProductDto != null) {
            return from(fakeStoreProductDto);
        }
        return null;
    }

    /**
     * This method creates a new product by sending a POST request to the FakeStore API.
     *
     * @param product the product to be created
     * @return the created product, or null if the creation failed
     */
    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDtoInput = from(product);
        FakeStoreProductDto output = fakeStoreApiClient.createFakeStoreProduct(fakeStoreProductDtoInput);
        if (output != null) {
            return from(output);
        }
        return null;
    }

    public Product replaceProduct(Product input, Long id) {
        FakeStoreProductDto fakeStoreProductDtoInput = from(input);
        FakeStoreProductDto output = fakeStoreApiClient.replaceFakeStoreProduct(fakeStoreProductDtoInput, id);
        if (output == null) return null;
        return from(output);
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    private FakeStoreProductDto from(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        if (product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

    private Product from(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
}