package com.example.productcatalogservice.dtos;

import com.example.productcatalogservice.models.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDto {
    private Long id;    //ToDo: why are we taking as input

    private String name;

    private String description;

    private List<Product> products;
}



//FetchType help in determining at what time (when), child entity will be
// loaded from Db to RAM
//FetchMode will help us in deciding how to get the data (join , subquery or select)