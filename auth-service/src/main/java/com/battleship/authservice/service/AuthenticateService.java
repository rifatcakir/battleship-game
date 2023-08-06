package com.battleship.authservice.service;

import com.battleship.authservice.jwt.JwtUtils;
import com.battleship.authservice.model.User;
import com.battleship.authservice.payload.request.SignUpRequest;
import com.battleship.authservice.payload.response.JWTResponse;
import com.battleship.authservice.payload.response.MessageResponse;
import com.battleship.authservice.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticateService {

    private final UserService userService;
    private final PasswordEncoder encoder;


    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public JWTResponse authenticateUser(String username, String password) {
        return createToken(username, password);
    }


    private JWTResponse createToken(String username, String password) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = getAuthentication(usernamePasswordAuthenticationToken);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        return getJwtResponse(userDetails);
    }

    private JWTResponse getJwtResponse(CustomUserDetails userDetails) {
        String jwt = jwtUtils.generateJwtToken(userDetails.getUsername());
        JWTResponse jwtResponse = new JWTResponse();
        jwtResponse.setToken(jwt);
        return jwtResponse;
    }

    private Authentication getAuthentication(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }
}
