package com.example.shop.orderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderItemService {

    Boolean isOrderItemExist(OrderItemDto orderItemDto);

    // OrderItemDto createOrderItem(OrderItemDto orderItemDto); 

    Optional<OrderItemDto> getOrderItemById(UUID id);

    List<OrderItemDto> getAllOrderItems();

    void deleteOrderItems(UUID id);

    OrderItemDto updateOrderItems(OrderItemDto orderItemDto);

}
