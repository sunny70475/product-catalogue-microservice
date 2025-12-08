package com.example.productcatalogservice.providerStrategy;

import com.example.productcatalogservice.models.Product;

import java.util.List;

//create provider interface (Strategy Pattern)
public interface ProductProvider {
    Product getProductById(Long id);

    Product createProduct(Product product);

    Product replaceProduct(Product input, Long id);

    List<Product> getAllProducts();

    List<Product> createMultipleProducts(List<Product> products);
}
