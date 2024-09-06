package com.example.shop.customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService)
    {
        this.customerService = customerService;
    }

    @PostMapping(path = "/add")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody final CustomerDTO customerDTO){
        
        System.out.println("Controller");

        final CustomerDTO savedCustomer = customerService.save(customerDTO);

        return new ResponseEntity<CustomerDTO>(savedCustomer, HttpStatus.CREATED);

        // if(isExist){
            // return new ResponseEntity<CustomerDTO>(savedCustomer, HttpStatus.OK);
        // }else{
        // }
    }

    @GetMapping(path =  "/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable final UUID id){
        
        Optional<CustomerDTO> foundedCustomer = customerService.findById(id);
        // System.out.println("From Controller-> " + foundedCustomer);
        return foundedCustomer.map(customer -> new ResponseEntity<CustomerDTO>(customer, HttpStatus.OK))
            .orElse(new ResponseEntity<CustomerDTO>(HttpStatus.NOT_FOUND));

    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return new ResponseEntity<List<CustomerDTO>>(customerService.getAllCustomers(), HttpStatus.OK);
    } 

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable final UUID id){
        customerService.deleteCustomerById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable final UUID id ,@RequestBody final CustomerDTO customerDTO){
        customerDTO.setId(id);
        final boolean isExist = customerService.isCustomerExists(customerDTO);

        if (isExist) {
            final CustomerDTO updatedCustomer = customerService.updateCustomer(id ,customerDTO);
            return new ResponseEntity<CustomerDTO>(updatedCustomer, HttpStatus.OK); 
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
