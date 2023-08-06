package com.battleship.authservice.mock;

import com.battleship.authservice.model.User;
import com.battleship.authservice.repository.UserRepository;
import com.battleship.authservice.service.UserService;
import com.battleship.authservice.utils.MockObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void shouldCallExistsByName() {

        String username = "John";

        given(userRepository.existsByUsername(anyString())).willReturn(false);

        // when
        userService.existsByUsername(username);

        // then
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    public void shouldFindByName() {
        User mockUser = MockObject.user();

        // when
        userService.findByUsername(mockUser.getUsername());

        // then
        verify(userRepository, times(1)).findByUsername(mockUser.getUsername());
    }

    @Test
    public void shouldSaveUser() {
        User mockUser = MockObject.user();

        given(userRepository.save(mockUser)).willReturn(mockUser);

        // when
        userService.saveUser(mockUser);

        // then
        verify(userRepository, times(1)).save(mockUser);
    }
}
