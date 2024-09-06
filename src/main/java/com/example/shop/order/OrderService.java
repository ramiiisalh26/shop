package com.example.shop.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;



public interface OrderService {

    Boolean IsOrderExists(OrderDTO orderDTO);

    OrderDTO save(OrderDTO orderDTO);

    OrderDTO createOrder(OrderDTO orderDTO);

    Optional<OrderDTO> findById(UUID id);

    List<OrderDTO> getAllOrders();

    void deleteOrderById(UUID id);

    OrderDTO updateOrder(OrderDTO orderDTO, UUID id);

    List<OrderDTO> findOrdersByCustomerId(UUID id);

    List<OrderDTO> findOrdersByAddressId(UUID id);

    // OrderItemDto createOrderItem(OrderItemDto orderItemDto, OrderDTO orderDTO);

}
