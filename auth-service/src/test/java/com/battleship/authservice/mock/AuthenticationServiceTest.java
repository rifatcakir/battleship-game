package com.battleship.authservice.mock;

import com.battleship.authservice.jwt.JwtUtils;
import com.battleship.authservice.security.CustomUserDetails;
import com.battleship.authservice.service.AuthenticateService;
import com.battleship.authservice.utils.MockObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private CustomUserDetails customUserDetails;

    @InjectMocks
    private AuthenticateService authenticateService;

    @Test
    public void shouldRegisterUser() {

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(authentication.getPrincipal()).willReturn(customUserDetails);
        given(authenticationManager.authenticate(any())).willReturn(authentication);
        given(securityContext.getAuthentication().getPrincipal()).willReturn(customUserDetails);


        var mockUser = MockObject.user();
        var mockToken = "mockjwt";
        given(jwtUtils.generateJwtToken(any())).willReturn(mockToken);

        SecurityContextHolder.setContext(securityContext);

        var response = authenticateService.authenticateUser(mockUser.getUsername(), mockUser.getPassword());

        assertThat(response.getToken(), is(equalTo(mockToken)));
    }


}
