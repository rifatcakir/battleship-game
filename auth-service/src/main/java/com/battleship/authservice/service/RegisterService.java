package com.battleship.authservice.service;

import com.battleship.authservice.model.User;
import com.battleship.authservice.payload.request.SignUpRequest;
import com.battleship.authservice.payload.response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterService {

    private final UserService userService;
    private final PasswordEncoder encoder;

    public MessageResponse registerUser(String username,String password) {
        if (userService.existsByUsername(username)) {
            return new MessageResponse("Error: Username is already taken!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));

        userService.saveUser(user);

        return new MessageResponse("User registered successfully!");
    }
}
