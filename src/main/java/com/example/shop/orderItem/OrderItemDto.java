package com.example.shop.orderItem;

import java.util.UUID;

import com.example.shop.product.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderItemDto {
    UUID id;
    Product product;
    int quantity;
    double totalPrice;
    String productOptions;
    double discount; 

}
