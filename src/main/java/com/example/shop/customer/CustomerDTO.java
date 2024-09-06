package com.example.shop.customer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.shop.address.AddressDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerDTO {
    UUID id;
    String first_name;
    String last_name;
    LocalDate date_of_birth;
    String gender;
    String phone_number;
    List<AddressDTO> address = new ArrayList<>();
    String username;
    String password;
    AccountStatus account_status;
    Date account_creation_date; 
}
