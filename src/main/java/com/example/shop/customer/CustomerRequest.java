package com.example.shop.customer;

import java.util.Date;

import com.example.shop.address.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String first_name;
    private String last_name;
    private Date date_of_birth;
    private String gender;
    private String phone_number;
    private Address address;
    private String username;
    private String password;
    private AccountStatus account_status;
    private Date account_creation_date;
}
