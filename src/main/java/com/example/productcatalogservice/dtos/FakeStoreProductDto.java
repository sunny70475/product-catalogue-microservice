package com.example.productcatalogservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FakeStoreProductDto {
    private String title;
    private String description;
    private String category;
    private String image;
    private Long id;
    private Double price;
}
