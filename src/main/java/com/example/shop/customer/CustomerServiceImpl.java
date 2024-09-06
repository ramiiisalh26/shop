package com.example.shop.customer;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.shop.address.AddressDTO;
import com.example.shop.address.AddressFor;
import com.example.shop.address.AddressService;
import com.example.shop.security.auth.AuthenticationServices;
import com.example.shop.security.auth.RegisterRequest;
import com.example.shop.user.Role;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService{

    private CustomerRepositry customerRepositry;
    private AddressService addressService;
    // private OrderService orderService;
    private AuthenticationServices authenticationServices;
    
    @Autowired
    public CustomerServiceImpl(
        final CustomerRepositry customerRepositry,
        final AuthenticationServices authenticationServices,
        final AddressService addressService
        // final OrderService orderService
        )
    {
        this.customerRepositry = customerRepositry;
        this.addressService = addressService;
        this.authenticationServices = authenticationServices;
        // this.orderService = orderService;
    }

    @Override
    public Boolean isCustomerExists(CustomerDTO customerDTO) {
        // System.out.println(customerDTO);
       return customerRepositry.existsById(customerDTO.getId());
    }

    @Transactional
    @Override
    public CustomerDTO save(CustomerDTO customerDTO){

        if (customerDTO == null) { throw new RuntimeException("Customer must be provided"); }

        System.out.println("Hello");
        // RegisterRequest register = RegisterRequest.builder()
        //     .first_name(customerDTO.getFirst_name())
        //     .last_name(customerDTO.getLast_name())
        //     .email(customerDTO.getUsername())
        //     .password(customerDTO.getPassword())
        //     .role(Role.USER)
        //     .build();

        // try {
        //     authenticationServices.register(register);            
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }

        customerDTO.setAccount_creation_date(Date.from(Instant.now()));

        Customer customer = CustomerMapper.fromDTOToEntity(customerDTO);

        Customer savedCustomer = customerRepositry.save(customer);
        customerDTO.setId(savedCustomer.getId());
        
        for(int i=0;i<customerDTO.getAddress().size();i++){     
            customerDTO.getAddress().get(i).setAddressFor(AddressFor.CUSTOMER);
            addressService.save(customerDTO.getAddress().get(i),customerDTO);
        }
        
        return CustomerMapper.fromEntityToDto(savedCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customerDTO) {
        Customer customer = customerRepositry.getCustomerById(id).orElseThrow(); // address ID is "Null" !!!
        List<AddressDTO> address = addressService.getAddressesByCustomerId(id);
        System.out.println(address);

        // Set Address from customerDTO
        for(int i=0;i<address.size();i++){
            if(address.get(i).getId().equals(customer.getAddress().get(i).getId())){ // this will be update another Time !!!
                addressService.updateAddress(customerDTO.getAddress().get(i), address.get(i).getId());
            }
        }
        
        // Set customer from customerDTO
        customer.setFirst_name(customerDTO.getFirst_name());
        customer.setLast_name(customerDTO.getLast_name());
        customer.setPhone_number(customerDTO.getPhone_number());
        customer.setAccount_status(customerDTO.getAccount_status());
        customer.setDate_of_birth(customerDTO.getDate_of_birth());
        customer.setGender(customerDTO.getGender());
        customer.setUsername(customerDTO.getUsername());

        // Wrong way
        // customer.setAddress(customerDTO.getAddress().stream().map(Address::fromDTOToEntity).toList()); 
        
        Customer updatedCustomer = customerRepositry.save(customer);
        return CustomerMapper.fromEntityToDto(updatedCustomer);
    }

    @Override
    public Optional<CustomerDTO> findById(UUID id) {
        
        Optional<Customer> customer = customerRepositry.findById(id);
        
        if (customer.isPresent()) {    
            return Optional.of(CustomerMapper.fromEntityToDto(customer.get()));
        }else{
            return Optional.empty();
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepositry.findAll();
        List<CustomerDTO> customerDTOs = customers.stream().map(customer -> CustomerMapper.fromEntityToDto(customer)).collect(Collectors.toList());
        return customerDTOs;
    }

    @Override
    public void deleteCustomerById(UUID id) {   
        
        Customer customer = customerRepositry.findById(id).get();
        customer.setAddress(null);
        customerRepositry.save(customer);
        
        List<AddressDTO> addressesDTO = addressService.getAddressesByCustomerId(id);
        for (AddressDTO addressDTO : addressesDTO) {
            addressService.deleteAddressById(addressDTO.getId());
        } 
        
        customerRepositry.deleteById(id);
    }
    
}
