package com.battleship.gamelobby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game-lobby")
public class TestController {

    @GetMapping
    public ResponseEntity<?> getName() {
        return ResponseEntity.ok("HELLOW");
    }
}
