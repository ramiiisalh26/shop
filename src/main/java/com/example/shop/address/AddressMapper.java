package com.example.shop.address;

public class AddressMapper {
    
    public static Address fromDTOToEntity(AddressDTO addressDTO){
        return Address.builder()
            .id(addressDTO.getId())
            .city(addressDTO.getCity())
            .country(addressDTO.getCountry())
            .state(addressDTO.getState())
            .street_address(addressDTO.getStreet_address())
            .zip(addressDTO.getZip())
            .addressFor(addressDTO.getAddressFor())
            .build();
    }

    public static AddressDTO fromEntityToDto(Address address){
        return AddressDTO.builder()
            .id(address.getId())
            .city(address.getCity())
            .country(address.getCountry())
            .state(address.getState())
            .zip(address.getZip())
            .street_address(address.getStreet_address())
            .addressFor(address.getAddressFor())
            .build();
    }
}
