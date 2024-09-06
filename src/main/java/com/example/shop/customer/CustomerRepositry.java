package com.example.shop.customer;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepositry extends JpaRepository<Customer, UUID>{
    
    Optional<Customer> getCustomerByUsername(String username);

    Optional<Customer> getCustomerById(UUID id);

    // List<Customer> getAllCustomers();



}
