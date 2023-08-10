package com.battleship.gamelobby;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

@Component
public class GameLobbyUtil {

    @Autowired
    private ObjectMapper objectMapper;

    public <T> T mapMvcResultToObject(MvcResult response, Class<T> object) throws UnsupportedEncodingException, JsonProcessingException {
        String content = response.getResponse().getContentAsString();
        return objectMapper.readValue(content, object);
    }

    public <T> T mapMvcResultToObjects(MvcResult response, TypeReference<T> objects) throws UnsupportedEncodingException, JsonProcessingException {
        String content = response.getResponse().getContentAsString();
        return objectMapper.readValue(content, objects);
    }
}
