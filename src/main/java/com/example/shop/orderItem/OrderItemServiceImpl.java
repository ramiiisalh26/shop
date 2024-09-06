package com.example.shop.orderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OrderItemServiceImpl implements OrderItemService{

    private OrderItemRepositry orderItemRepositry;
    
    @Autowired
    public OrderItemServiceImpl(final OrderItemRepositry orderItemRepositry) {
        this.orderItemRepositry = orderItemRepositry;
    }


    @Override
    public Boolean isOrderItemExist(OrderItemDto orderItemDto) {
        return orderItemRepositry.existsById(orderItemDto.getId());
    }

    @Override
    public Optional<OrderItemDto> getOrderItemById(UUID id) {
        Optional<OrderItem> orderItem = orderItemRepositry.findById(id);
        if (orderItem.isPresent()) {
            return Optional.of(OrderItemMapper.fromEntityToDto(orderItem.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepositry.findAll();
        List<OrderItemDto> orderItemsDto = orderItems.stream().map(orderItem -> OrderItemMapper.fromEntityToDto(orderItem)).collect(Collectors.toList()); 
        return orderItemsDto;
    }

    @Override
    public void deleteOrderItems(UUID id) {
        orderItemRepositry.deleteById(id);
    }

    @Override
    public OrderItemDto updateOrderItems(OrderItemDto orderItemDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrderItems'");
    }

}
