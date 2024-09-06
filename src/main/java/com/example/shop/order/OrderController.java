package com.example.shop.order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO){
        
        final OrderDTO savedOrder = orderService.createOrder(orderDTO);

        return new ResponseEntity<OrderDTO>(savedOrder,HttpStatus.CREATED);
    } 

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id){
        Optional<OrderDTO> orderDto = orderService.findById(id);

        return orderDto.map(order -> new ResponseEntity<OrderDTO>(order,HttpStatus.OK))
                        .orElse(new ResponseEntity<OrderDTO>(HttpStatus.NOT_FOUND));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<OrderDTO>> getAllOrders(){
        return new ResponseEntity<List<OrderDTO>>(orderService.getAllOrders(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteOrderById(@PathVariable UUID id){
        orderService.deleteOrderById(id);
        return new ResponseEntity<OrderDTO>(HttpStatus.NO_CONTENT);
    }
}
