package com.example.shop.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.shop.address.Address;
import com.example.shop.customer.Customer;
import com.example.shop.orderItem.OrderItemDto;
import com.example.shop.payment.PaymentDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDTO {
    UUID id;
    Customer customer;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    double totalAmount;
    PaymentDetails paymentDetails;
    Address shippingAddress;
    Address billingAddress;
    String shippingMethod;
    List<OrderItemDto> orderItems; // here granular to be "Set" not "list"
}
