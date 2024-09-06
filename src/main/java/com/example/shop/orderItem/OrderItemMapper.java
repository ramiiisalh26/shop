package com.example.shop.orderItem;

public class OrderItemMapper {
    
    public static OrderItem fromDtoToEntity(OrderItemDto orderItemDto){    
        return OrderItem.builder()
                .product(orderItemDto.getProduct())
                .quantity(orderItemDto.getQuantity())
                .totalPrice(orderItemDto.getTotalPrice())
                .productOptions(orderItemDto.getProductOptions())
                .discount(orderItemDto.getDiscount())
                .build();
    }

    public static OrderItemDto fromEntityToDto(OrderItem orderItem){
        return OrderItemDto.builder()
                .product(orderItem.getProduct())
                .quantity(orderItem.getQuantity())
                .totalPrice(orderItem.getTotalPrice())
                .productOptions(orderItem.getProductOptions())
                .discount(orderItem.getDiscount())
                .build();
    }

}
