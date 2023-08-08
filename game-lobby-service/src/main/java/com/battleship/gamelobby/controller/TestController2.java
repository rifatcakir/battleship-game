package com.battleship.gamelobby.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestController2 {

    @GetMapping
    public ResponseEntity<String> getName() {
        return ResponseEntity.ok("HELLOW");
    }
}
