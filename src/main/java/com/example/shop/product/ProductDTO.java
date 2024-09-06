package com.example.shop.product;

import java.util.Date;
import java.util.UUID;

import com.example.shop.category.Category;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDTO {
    UUID id;
    String name;
    String description;
    double price;
    Date add_time_creation;
    Category category;
}
