package com.example.shop.security.token;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenServices {
    
    private final TokenRepository tokenRepository;

    public void saveToken(Token token){
        tokenRepository.save(token);
    }

    public Optional<Token> getToken(String token){
        return tokenRepository.findByToken(token);
    }

    public int setConfirmedAt(String token){
        return tokenRepository.updateConfirmedAt(token,LocalDateTime.now());
    }

    public List<Token> findAllValidTokenByUser(UUID id){
        return tokenRepository.findAllValidTokenByUser(id);
    }

    public List<Token> saveAll(List<Token> token){
        return tokenRepository.saveAll(token);
    }
    
}
