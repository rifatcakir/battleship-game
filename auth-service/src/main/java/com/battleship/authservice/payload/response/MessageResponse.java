package com.battleship.authservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
public class MessageResponse extends RepresentationModel<MessageResponse> {
    private String message;
}
