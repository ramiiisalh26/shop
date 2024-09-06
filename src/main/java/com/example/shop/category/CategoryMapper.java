package com.example.shop.category;

import com.example.shop.product.ProductMapper;

public class CategoryMapper {
    
    public static Category fromDTOToEntity(CategoryDTO categoryDTO){
        return Category.builder()
        .id(categoryDTO.getId())
        .name(categoryDTO.getName())
        .description(categoryDTO.getDescription())
        .category_creation_date(categoryDTO.getCategory_creation_date())
        .products(categoryDTO.getProducts().stream().map(ProductMapper::fromDTOToEntity).toList())
        .build();
    }

    public static CategoryDTO fromEntityToDto(Category category){
        return CategoryDTO.builder()
            .id(category.getId())
            .name(category.getName())
            .description(category.getDescription())
            .category_creation_date(category.getCategory_creation_date())
            // .products(category.getProducts().stream().map(ProductDTO::fromEntityToDto).toList())
            .build();
    }
}
