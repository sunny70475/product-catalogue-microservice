package com.example.productcatalogservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel{
    private String name;
    private String description;
    // mappedBy = "category" indicates that the 'products' field in Product class is the owner of the relationship.
    @OneToMany(mappedBy = "category")
    private List<Product> products;
}
