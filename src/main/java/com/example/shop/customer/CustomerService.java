package com.example.shop.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {
    
    Boolean isCustomerExists(CustomerDTO customerDTO);

    CustomerDTO save(CustomerDTO customerDTO);

    Optional<CustomerDTO> findById(UUID id);

    List<CustomerDTO> getAllCustomers();

    void deleteCustomerById(UUID id);

    CustomerDTO updateCustomer( UUID id ,CustomerDTO customerDTO);

}
