package com.example.productcatalogservice.config;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import com.example.productcatalogservice.models.Product;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class is used to configure ModelMapper bean for mapping between DTOs and entities.
@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        // Product -> FakeStoreProductDto
        // src extracts category name from Product's Category object (if not null) and later sets it to FakeStoreProductDto's category field.
        mapper.typeMap(Product.class, FakeStoreProductDto.class)
                .addMapping(
                        src -> src.getCategory() != null ? src.getCategory().getName() : null,
                        FakeStoreProductDto::setCategory
                );


        // FakeStoreProductDto -> Product (skip category, handled manually)
        mapper.typeMap(FakeStoreProductDto.class, Product.class)
                .addMappings(m -> m.skip(Product::setCategory));

        return mapper;
    }
}

//FakeStoreProductDto.category is a String
//Product.category is a Category object