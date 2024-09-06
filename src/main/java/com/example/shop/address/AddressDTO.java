package com.example.shop.address;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    UUID id;
    String street_address;
    String city;
    String state;
    String zip;
    String country;
    AddressFor addressFor;
}
