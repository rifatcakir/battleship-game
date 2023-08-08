package com.battleship.gamelobby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/game-lobby")
public class TestController {

    @GetMapping("/a")
    @PreAuthorize("hasAuthority('ROLE_PLAYER')")
    public ResponseEntity<?> getName() {
        return ResponseEntity.ok("HELLOW");
    }
}
