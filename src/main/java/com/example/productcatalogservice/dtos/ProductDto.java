package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
public class ProductDto {

        private Long id;  //ToDo: why are we taking as input
        @NonNull
        private String name;

        private String description;

        private Double price;

        private String imageUrl;

        private CategoryDto category;

}
