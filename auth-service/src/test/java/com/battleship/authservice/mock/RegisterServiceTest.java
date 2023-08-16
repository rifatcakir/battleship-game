package com.battleship.authservice.mock;

import com.battleship.authservice.exception.UserExistsException;
import com.battleship.authservice.model.User;
import com.battleship.authservice.service.RegisterService;
import com.battleship.authservice.service.UserService;
import com.battleship.authservice.utils.MockObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @InjectMocks
    private RegisterService registerService;

    @Test
    public void shouldRegisterUser() {
        User mockUser = MockObject.user();
        given(userService.existsByUsername(mockUser.getUsername())).willReturn(false);

        mockUser.setPassword("encodedPassword");
        given(passwordEncoder.encode(any())).willReturn(mockUser.getPassword());

        var registerResponse = registerService.registerUser(mockUser.getUsername(), mockUser.getPassword());

        verify(userService, times(1)).saveUser(mockUser);

        assertThat(registerResponse.getMessage(), is(equalTo("User registered successfully!")));
    }

    @Test
    public void whenUserAlreadyRegisteredReturnExistsMessage() {
        User mockUser = MockObject.user();
        given(userService.existsByUsername(mockUser.getUsername())).willReturn(true);

        assertThrows(UserExistsException.class, () -> registerService.registerUser(mockUser.getUsername(), mockUser.getPassword()));
    }

}
