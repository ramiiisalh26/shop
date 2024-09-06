package com.example.shop.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    
    @JsonProperty("access_token") //come back 
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
