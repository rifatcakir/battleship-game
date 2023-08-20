package com.battleship.authservice.integration;

import com.battleship.authservice.repository.UserRepository;
import com.battleship.authservice.utils.MockObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    private final String baseUrl = "/authenticate";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();
    }

    @Test
    void creatingNewUserShouldReturns200WhenValidInput() throws Exception {
        //given
        var request = MockObject.signUpRequest();

        //when + then
        mockMvc.perform(post(baseUrl + "/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        assertThat(userRepository.findByUsername(request.getUsername())).isPresent();
    }

    @Test
    @WithAnonymousUser
    void creatingExistingUserShouldReturnError() throws Exception {
        //given
        userRepository.save(MockObject.user());
        var request = MockObject.signUpRequest();

        assertThat(userRepository.findByUsername(request.getUsername())).isPresent();

        //when + then
        mockMvc.perform(post(baseUrl + "/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void loginWithValidUserShouldReturn200() throws Exception {
        //given
        var request = MockObject.signUpRequest();

        mockMvc.perform(post(baseUrl + "/signup")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());


        //then
        mockMvc.perform(post(baseUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithAnonymousUser
    void loginWithNotExistUserShouldReturn404() throws Exception {
        //given
        var request = MockObject.loginRequest();

        //when + then
        mockMvc.perform(post(baseUrl + "/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenNoUserLoggedIn_whenCallSwagger_thenOk() throws Exception {
        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isOk());
    }
}
