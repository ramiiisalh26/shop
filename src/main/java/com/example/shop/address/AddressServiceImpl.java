package com.example.shop.address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.customer.CustomerDTO;
import com.example.shop.customer.CustomerMapper;

@Service
public class AddressServiceImpl implements AddressService{
    
    private AddressRepositry addressRepositry;

    public AddressServiceImpl(final AddressRepositry addressRepositry){
        this.addressRepositry = addressRepositry;
    }

    @Transactional
    @Override
    public AddressDTO save(AddressDTO addressDTO, CustomerDTO customerDTO){
        
        if (addressDTO == null) throw new RuntimeException("Address must be provided");
        
        Optional<Address> existingAddress = addressRepositry.findByCustomerAndDetails(
            CustomerMapper.fromDTOToEntity(customerDTO),
            addressDTO.getStreet_address(),
            addressDTO.getCity(),
            addressDTO.getState(),
            addressDTO.getZip(),
            addressDTO.getCountry()
        );

        if (existingAddress.isPresent()) {
            return AddressMapper.fromEntityToDto(existingAddress.get());
        }else{
            Address address = Address.builder()
            .city(addressDTO.getCity())
            .country(addressDTO.getCountry())
            .state(addressDTO.getState())
            .zip(addressDTO.getZip())
            .street_address(addressDTO.getStreet_address())
            .addressFor(addressDTO.getAddressFor())
            .customer(CustomerMapper.fromDTOToEntity(customerDTO))
            .build();

            addressRepositry.save(address);
            return AddressMapper.fromEntityToDto(address);
        }
        // System.out.println(address);
    }

    

    @Override
    public void save(AddressDTO billingaddressDTO ,AddressDTO shippinggaddressDTO ,CustomerDTO customerDTO){
        
        if (billingaddressDTO == null && shippinggaddressDTO == null ) throw new RuntimeException("Address must be provided");
        
        AddressDTO addressDTOs[] = new AddressDTO[2];
        addressDTOs[0] = billingaddressDTO;
        addressDTOs[1] = shippinggaddressDTO;
        
        for(int i=0;i<addressDTOs.length;i++){
            Optional<Address> existingAddress = addressRepositry.findByCustomerAndDetails(
                CustomerMapper.fromDTOToEntity(customerDTO),
                addressDTOs[i].getStreet_address(),
                addressDTOs[i].getCity(),
                addressDTOs[i].getState(),
                addressDTOs[i].getZip(),
                addressDTOs[i].getCountry()
            );

            if (existingAddress.isPresent()) {
                continue;
            }else{
                Address address = Address.builder()
                .city(addressDTOs[i].getCity())
                .country(addressDTOs[i].getCountry())
                .state(addressDTOs[i].getState())
                .zip(addressDTOs[i].getZip())
                .street_address(addressDTOs[i].getStreet_address())
                .addressFor(addressDTOs[i].getAddressFor())
                .build();
                address.setCustomer(CustomerMapper.fromDTOToEntity(customerDTO));
                addressRepositry.save(address);
            }
        }
    }
    
    @Override
    public List<AddressDTO> getAddressesByCustomerId(UUID id){
        List<Address> addresses = addressRepositry.findByCustomerId(id);
        List<AddressDTO> addressDTOs = addresses.stream().map(address -> AddressMapper.fromEntityToDto(address)).collect(Collectors.toList());
        return addressDTOs;
    }

    @Transactional
    @Override
    public void deleteAddressById(UUID id){
        addressRepositry.deleteById(id);
    }

    @Transactional
    @Override
    public AddressDTO updateAddress(AddressDTO addressDTO, UUID id) {
        Optional<Address> address = addressRepositry.findById(id);

        if (!address.isPresent()) throw new RuntimeException("Address Not Found");

        address.get().setCity(addressDTO.getCity());
        address.get().setCountry(addressDTO.getCountry());
        address.get().setState(addressDTO.getState());
        address.get().setStreet_address(addressDTO.getStreet_address());
        address.get().setZip(addressDTO.getZip());

        Address savedAddress = addressRepositry.save(address.get());
        return AddressMapper.fromEntityToDto(savedAddress);
    }

    @Override
    public AddressDTO getAddressById(UUID id) {
        return null;
    }

    // @Override
    // public List<Address> getAddressByOrderTarget(AddressFor addressFor) {
    //     return addressRepositry;
    // }

}
