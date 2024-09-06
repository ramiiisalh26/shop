package com.example.shop.product;

public class ProductMapper {
    
    public static Product fromDTOToEntity(ProductDTO productDTO){
        return Product.builder()
            .id(productDTO.getId())
            .name(productDTO.getName())
            .description(productDTO.getDescription())
            .price(productDTO.getPrice())
            .add_time_creation(productDTO.getAdd_time_creation())
            .category(productDTO.getCategory())
            .build();
    }

    public static ProductDTO fromEntityToDto(Product product){
        return ProductDTO.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .add_time_creation(product.getAdd_time_creation())
            // .category(product.getCategory())
            .build();
    }
}
