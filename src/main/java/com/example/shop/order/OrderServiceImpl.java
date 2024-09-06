package com.example.shop.order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.address.AddressFor;
import com.example.shop.customer.CustomerDTO;
import com.example.shop.customer.CustomerMapper;
import com.example.shop.customer.CustomerService;
import com.example.shop.orderItem.OrderItem;
import com.example.shop.orderItem.OrderItemDto;
import com.example.shop.orderItem.OrderItemRepositry;
import com.example.shop.product.ProductDTO;
import com.example.shop.product.ProductMapper;
import com.example.shop.product.ProductService;

@Service
public class OrderServiceImpl implements OrderService{

    private OrderRepositry orderRepositry;
    private CustomerService customerService;
    private ProductService productService;
    private OrderItemRepositry orderItemRepositry;

    @Autowired
    public OrderServiceImpl(final OrderRepositry orderRepositry,
                            final ProductService productService,
                            final OrderItemRepositry orderItemRepositry,
                            final CustomerService customerService) {
        this.orderRepositry = orderRepositry;
        this.productService = productService;
        this.orderItemRepositry = orderItemRepositry;
        this.customerService = customerService;
    }

    @Override
    public Boolean IsOrderExists(OrderDTO orderDTO) {
        return orderRepositry.existsById(orderDTO.getId());
    }

    @Override
    public OrderDTO save(OrderDTO orderDTO) {
        Order order = OrderMapper.fromDtoToEntity(orderDTO);
        orderRepositry.save(order);
        return OrderMapper.fromEntityToDto(order);
    }

    @Transactional
    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        
        if (orderDTO == null) throw new RuntimeException("Order Must be provided");
        
        if(orderDTO.getOrderItems().isEmpty()) throw new RuntimeException("Order items is Empty");
        
        Optional<CustomerDTO> customerDto = customerService.findById(orderDTO.getCustomer().getId());

        if (!customerDto.isPresent()) throw new RuntimeException("Customer info not provided");

        orderDTO.setCustomer(CustomerMapper.fromDTOToEntity(customerDto.get()));

        orderDTO.setOrderDate(LocalDateTime.now());

        if(orderDTO.getShippingAddress().equals(orderDTO.getBillingAddress())){
            
            orderDTO.getBillingAddress().setAddressFor(AddressFor.BILLANDSHIP);
            orderDTO.getBillingAddress().setCustomer(orderDTO.getCustomer());

            orderDTO.setShippingAddress(null);
        
        } else {            
            orderDTO.getBillingAddress().setAddressFor(AddressFor.BILLING);
            orderDTO.getShippingAddress().setAddressFor(AddressFor.SHIPPING);

            orderDTO.getBillingAddress().setCustomer(orderDTO.getCustomer());
            orderDTO.getShippingAddress().setCustomer(orderDTO.getCustomer());
        }
                
        Order order = OrderMapper.fromDtoToEntity(orderDTO);
        
        Order savedOrder = orderRepositry.save(order);

        orderDTO.getOrderItems().stream()
            .map(itemDto -> {
                OrderItem orderItem = createOrderItem(itemDto, savedOrder);
                orderItem.setOrder(savedOrder);
                return orderItem;
            })
            .collect(Collectors.toList());
        
        double total = 0.0;
        for(int i=0;i<orderDTO.getOrderItems().size();i++){
            total += orderDTO.getOrderItems().get(i).getTotalPrice();
        }
        System.out.println(total);
        orderDTO.setTotalAmount(total);
        
        orderRepositry.updateTotalAmount(total,savedOrder.getId());

        return orderDTO;
    }

    @Transactional
    private OrderItem createOrderItem(OrderItemDto orderItemDto, Order order) {

        if(orderItemDto == null) throw new RuntimeException("OrderItem must be provided");

        ProductDTO productDto = productService.findById(orderItemDto.getProduct().getId()).get();

        double totalPriceBeforeDiscount = orderItemDto.getProduct().getPrice() * orderItemDto.getQuantity();
        double discountAmount = totalPriceBeforeDiscount * (orderItemDto.getDiscount() / 100);
        double finalPrice = totalPriceBeforeDiscount - discountAmount;

        orderItemDto.setTotalPrice(finalPrice);

        OrderItem orderItem = OrderItem.builder()
            .order(order)
            .product(ProductMapper.fromDTOToEntity(productDto))
            .productOptions(orderItemDto.getProductOptions())
            .quantity(orderItemDto.getQuantity())
            .totalPrice(orderItemDto.getTotalPrice())
            .discount(orderItemDto.getDiscount())
            .build();    

        OrderItem savedItem = orderItemRepositry.save(orderItem);
    
        return savedItem;

    }

    @Override
    public Optional<OrderDTO> findById(UUID id) {
        
        Optional<Order> order = orderRepositry.findById(id);

        if (!order.isPresent()){
            throw new RuntimeException("Order Not Found");
        }  
        
        return Optional.of(OrderMapper.fromEntityToDto(order.get()));
    }    

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepositry.findAll();
        List<OrderDTO> orderDTOs = orders.stream().map(order -> OrderMapper.fromEntityToDto(order)).collect(Collectors.toList());
        return orderDTOs;
    }

    @Override
    public void deleteOrderById(UUID id) {
        
        Order currentOrder = orderRepositry.findById(id).get();
        List<Order> orders = orderRepositry.findOrdersByCustomerId(currentOrder.getCustomer().getId());
        
        // List<Address> addresses = new ArrayList<>();

        if (!orders.isEmpty()) {
            for(Order order : orders){
                if (order.getId() == id) {
                    // addresses.add(order.getBillingAddress());
                    // addresses.add(order.getShippingAddress());
                    order.setBillingAddress(null);
                    order.setShippingAddress(null);
                    order.setCustomer(null);
                    orderRepositry.save(order);
                    orderRepositry.deleteById(id);    
                }
            }    
        }

    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateOrder'");
    }

    @Override
    public List<OrderDTO> findOrdersByCustomerId(UUID id){
        List<Order> orders = orderRepositry.findOrdersByCustomerId(id);
        List<OrderDTO> ordersDTO = orders.stream().map(order -> OrderMapper.fromEntityToDto(order)).collect(Collectors.toList());
        return ordersDTO;
    }

    @Override
    public List<OrderDTO> findOrdersByAddressId(UUID id) {
        List<Order> orders = orderRepositry.findOrdersByAddressId(id);
        List<OrderDTO> ordersDTO = orders.stream().map(order -> OrderMapper.fromEntityToDto(order)).collect(Collectors.toList());
        return ordersDTO;
    }


}
