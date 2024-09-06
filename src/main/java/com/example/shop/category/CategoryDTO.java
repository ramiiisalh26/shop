package com.example.shop.category;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.shop.product.ProductDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDTO {
    UUID id;
    String name;
    String description;
    List<ProductDTO> products;
    Date category_creation_date;
    
}
