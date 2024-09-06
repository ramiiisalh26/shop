package com.example.shop.user;

import java.security.Principal;
// import java.time.LocalDateTime;
// import java.util.List;
import java.util.Optional;
// import java.util.UUID;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

// import com.example.shop.security.token.JwtService;
// import com.example.shop.security.token.Token;
// import com.example.shop.security.token.TokenRepository;
// import com.example.shop.security.token.TokenServices;

// import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServices {

    private final static String USER_NOT_FOUND = "user with email %s not found";

    private final PasswordEncoder passwordEncoder;
    private final UserRepositry userRepositry;


    public User signUpUser(User user){
        
        boolean userExist = userRepositry.findByUsername(user.getUsername()).isPresent();

        if(userExist){
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("Email already taken");
        }

        userRepositry.save(user);

        return user;

    }

    public int enableUser(String email){
        return userRepositry.enableUser(email);
    }

    public Optional<User> findByEmail(String email){
        return userRepositry.findByUsername(email);
    }

    public Boolean isEnabled(String email){
        return userRepositry.getEnbleUser(email);
    }

    public void changePassword(ChangePasswordRequest request, Principal connectedUser){
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong Password");
        }
        // check if the two new passwords are the same 
        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Password are not same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepositry.save(user);
    }
    
}
