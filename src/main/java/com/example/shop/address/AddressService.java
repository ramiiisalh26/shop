package com.example.shop.address;

import java.util.List;
import java.util.UUID;

import com.example.shop.customer.CustomerDTO;

public interface AddressService {

    AddressDTO save(AddressDTO addressDTO, CustomerDTO customerDTO);
    
    void save(AddressDTO billingaddressDTO ,AddressDTO shippinggaddressDTO ,CustomerDTO customerDTO);
    
    List<AddressDTO> getAddressesByCustomerId(UUID id);

    void deleteAddressById(UUID id);

    AddressDTO updateAddress(AddressDTO addressDTO, UUID id);

    AddressDTO getAddressById(UUID id);

    // List<Address> getAddressByOrderTarget(AddressFor addressFor);
}
