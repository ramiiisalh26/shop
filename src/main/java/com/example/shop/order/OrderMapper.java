package com.example.shop.order;

import java.util.stream.Collectors;

import com.example.shop.orderItem.OrderItemMapper;

public class OrderMapper {
    
    public static Order fromDtoToEntity(OrderDTO orderDto){
        return Order.builder()
            .id(orderDto.getId())
            .customer(orderDto.getCustomer())
            .orderDate(orderDto.getOrderDate())
            .orderStatus(orderDto.getOrderStatus())
            .totalAmount(orderDto.getTotalAmount())
            .paymentDetails(orderDto.getPaymentDetails())
            .shippingAddress(orderDto.getShippingAddress())
            .billingAddress(orderDto.getBillingAddress())
            .shippingMethod(orderDto.getShippingMethod())
            .orderItems(orderDto.getOrderItems().stream().map(OrderItemMapper::fromDtoToEntity).collect(Collectors.toList()))
            .build();
    }

    public static OrderDTO fromEntityToDto(Order order){
        return OrderDTO.builder()
            .id(order.getId())
            .customer(order.getCustomer())
            .orderDate(order.getOrderDate())
            .orderStatus(order.getOrderStatus())
            .totalAmount(order.getTotalAmount())
            .paymentDetails(order.getPaymentDetails())
            .shippingAddress(order.getShippingAddress())
            .billingAddress(order.getBillingAddress())
            .shippingMethod(order.getShippingMethod())
            .orderItems(order.getOrderItems().stream().map(OrderItemMapper::fromEntityToDto).toList())
            .build();
    } 
}
