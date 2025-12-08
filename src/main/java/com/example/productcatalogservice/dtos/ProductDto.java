package com.example.productcatalogservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

        private Long id;  // id should NOT be mandatory in request

        @NotBlank(message = "Product name is required")
        private String name;

        private String description;

        @NotNull(message = "Price is required")
        private Double price;

        private String imageUrl;

        private CategoryDto category;
}
