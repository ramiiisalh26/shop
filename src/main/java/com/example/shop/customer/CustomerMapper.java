package com.example.shop.customer;

import java.util.stream.Collectors;

import com.example.shop.address.AddressMapper;

public class CustomerMapper {

    public static CustomerDTO fromEntityToDto(Customer customer){
        return CustomerDTO.builder()
            .id(customer.getId())
            .first_name(customer.getFirst_name())
            .last_name(customer.getLast_name())
            .username(customer.getUsername())
            // .password(customer.getPassword())
            .date_of_birth(customer.getDate_of_birth())
            .gender(customer.getGender()) 
            .phone_number(customer.getPhone_number()) 
            // .account_creation_date(customer.getAccount_creation_date())
            .account_status(customer.getAccount_status())
            .address(customer.getAddress().stream().map(AddressMapper::fromEntityToDto).collect(Collectors.toList())) // how to access List here
            .build();
    }

    public static Customer fromDTOToEntity(CustomerDTO customerDTO){
        return Customer.builder()
            .id(customerDTO.getId())
            .first_name(customerDTO.getFirst_name())
            .last_name(customerDTO.getLast_name())
            .username(customerDTO.getUsername())
            .date_of_birth(customerDTO.getDate_of_birth())
            // .password(customerDTO.getPassword())
            .gender(customerDTO.getGender()) 
            .phone_number(customerDTO.getPhone_number()) 
            .address(customerDTO.getAddress().stream().map(AddressMapper::fromDTOToEntity).collect(Collectors.toList()))
            .account_creation_date(customerDTO.getAccount_creation_date())
            .account_status(customerDTO.getAccount_status())
            .build();
    }    
    
}
