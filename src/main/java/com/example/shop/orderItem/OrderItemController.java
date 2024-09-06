package com.example.shop.orderItem;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orderItem")
public class OrderItemController {
    
    private OrderItemService orderItemService;

    @Autowired
    public OrderItemController(final OrderItemService orderItemService){
        this.orderItemService = orderItemService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable final UUID id){
        Optional<OrderItemDto> foundedOrderItem = orderItemService.getOrderItemById(id);
        return foundedOrderItem.map(orderItem -> new ResponseEntity<OrderItemDto>(orderItem, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<OrderItemDto>> getAllOrdersItems(){
        List<OrderItemDto> orderItemDtos = orderItemService.getAllOrderItems();
        return new ResponseEntity<List<OrderItemDto>>(orderItemDtos,HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteOrderItemById(@PathVariable UUID id){
        orderItemService.deleteOrderItems(id);
    }

}
