package com.example.shop.security.auth;

import com.example.shop.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    
    private String first_name;
    private String last_name;
    private String email;
    private String password;
    private Role role;
    
}
