package com.example.productcatalogservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Product extends BaseModel {

    @Column(nullable = false)   // Prevent null name in DB
    private String name;

    private String description;

    private Double price;

    private String imageUrl;


    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;

}
