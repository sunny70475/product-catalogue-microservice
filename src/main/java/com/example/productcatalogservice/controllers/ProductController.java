package com.example.productcatalogservice.controllers;

import com.example.productcatalogservice.dtos.CategoryDto;
import com.example.productcatalogservice.dtos.ProductDto;
import com.example.productcatalogservice.models.Category;
import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProductService productService;
    @Autowired
    private RestControllerAdvisor restControllerAdvisor;

    @GetMapping ("/products")
    public List<Product> getAllProducts() {
        //Dummy Response
        Product product = new Product();
        product.setId(1L);
        product.setPrice(1000D);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        return productList;
    }

    // create a dummy API wrapper GetProductById where id will be input
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long productId) {
        // TODO : Handle null case
        // TODO : Handle negative case
         if (productId <= 0) {
            throw new IllegalArgumentException("Product Id not found");
           // restControllerAdvisor.handleExceptions(new IllegalArgumentException("Product Id not found"));
         }
        Product product = productService.getProductById(productId);
        if (product == null) return null;

        ProductDto productDto = from(product);
        return new ResponseEntity<>(productDto, HttpStatus.OK);

    }

    @PostMapping ("/products")
    public ProductDto createProduct(@RequestBody @Validated ProductDto productDto) {
        Product product = from(productDto);
        Product output = productService.createProduct(product);
        if(output == null) return null;
        return  from(output);
    }

    @PutMapping("/products/{id}")
    public ProductDto replaceProduct(@PathVariable Long id,@RequestBody ProductDto productDto) {
        Product product = from(productDto);
        Product output = productService.replaceProduct(product,id);
        if(output == null) return null;
        return  from(output);
    }


    // TODO : Use modelmapper here
    private ProductDto from (Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setImageUrl(product.getImageUrl());
        if (product.getCategory() != null) {
            CategoryDto categoryDto = new CategoryDto();
            categoryDto.setName(product.getCategory().getName());
            categoryDto.setId(product.getCategory().getId());
            categoryDto.setDescription(product.getCategory().getDescription());
            productDto.setCategory(categoryDto);
        }
        return productDto;
    }

    private Product from (ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        if (productDto.getCategory() != null) {
            Category category = new Category();
            category.setName(productDto.getCategory().getName());
            category.setId(productDto.getCategory().getId());
            category.setDescription(productDto.getCategory().getDescription());
            product.setCategory(category);
        }
        return product;
    }
}
