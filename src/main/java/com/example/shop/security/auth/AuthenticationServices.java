package com.example.shop.security.auth;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shop.email.EmailSender;
import com.example.shop.security.token.JwtService;
import com.example.shop.security.token.Token;
import com.example.shop.security.token.TokenServices;
import com.example.shop.security.token.TokenType;
import com.example.shop.user.User;
// import com.example.shop.user.userServices;
import com.example.shop.user.UserServices;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {
    
    private final UserServices userServices;
    private final TokenServices tokenServices;
    private final EmailValidator emailValidator;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtServices;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws Exception{
        System.out.println("From register");
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if(!isValidEmail) {
            throw new IllegalStateException("Email Not found");
        }

        var user = User.builder()
            .first_name(request.getFirst_name())
            .last_name(request.getLast_name())
            .username(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(request.getRole())
            .build();
        System.out.println(user);
        // var savedUser = userServices.save(user);
        var savedUser = userServices.signUpUser(user);
        var jwtToken = jwtServices.generateToken(user);
        // var refreshToken = jwtServices.generateRefreshToken(user);
        saveUserToken(savedUser,jwtToken); 
        
        String link = "http://localhost:8090/api/v1/auth/register/confirm?token=" + jwtToken;
        emailSender.send(request.getEmail() ,buildEmail(request.getFirst_name(), link));

        var authResponse = AuthenticationResponse.builder()
            .accessToken(jwtToken)
            // .refreshToken(refreshToken)
            .build();

        return authResponse;
    }

    public AuthenticationResponse authenticate(AuthenticatoinRequest request) throws Exception{

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        ));

        
        var user = userServices.findByEmail(request.getEmail()).orElseThrow();
        // var jwtToken = jwtServices.generateToken(user);
        var refreshToken = jwtServices.generateRefreshToken(user);
        revokeAllUserToken(user);
        saveUserToken(user, refreshToken);
        return AuthenticationResponse.builder()
            // .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
    }

    @Transactional
    public String confirmToken(String token){
        var confirmationToken = tokenServices
                    .getToken(token)
                    .orElseThrow(()->
                        new IllegalStateException("token not found")
                    );

        if(confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Eamil Already confirmed");
        }
        // LocalDateTime expiredAt = fromDateToLocalDateTime(confirmationToken.getExpiresAt());
        
        if (confirmationToken.getExpiresAt().before(new Date())) {
            throw new IllegalStateException();
        }

        tokenServices.setConfirmedAt(token);
        userServices.enableUser(confirmationToken.getUser().getUsername());

        return "confirmed Rami";
    }

    private void saveUserToken(User user, String jwtToken) throws Exception{
        var token = Token.builder()
                    .user(user)
                    .createdAt(jwtServices.extractClaim(jwtToken, Claims::getIssuedAt))
                    .expiresAt(jwtServices.extractClaim(jwtToken,Claims::getExpiration))
                    .token(jwtToken)
                    .tokenType(TokenType.BEARER)
                    .expired(false)
                    .revoked(false)
                    .build();
                    
        tokenServices.saveToken(token);
    }

    private void revokeAllUserToken(User user){
        var validUserTokens = tokenServices.findAllValidTokenByUser(user.getUser_id());
        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenServices.saveAll(validUserTokens);
    }

    // Come back soon 
    public void refreshToken(
        HttpServletRequest request,
        HttpServletResponse response
    )throws Exception{
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtServices.extractUsername(refreshToken);
        if(username != null){
            var user = this.userServices.findByEmail(username).orElseThrow();
            if (jwtServices.isTokenValid(refreshToken, user)) {
                var accessToken = jwtServices.generateToken(user);
                revokeAllUserToken(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public LocalDateTime fromDateToLocalDateTime(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
