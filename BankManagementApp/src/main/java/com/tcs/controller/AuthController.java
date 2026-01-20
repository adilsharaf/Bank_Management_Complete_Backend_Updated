package com.tcs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.dto.AuthResponseDTO;
import com.tcs.dto.LoginRequestDTO;
import com.tcs.dto.RegisterRequestDTO;
import com.tcs.service.AuthService;


@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
    AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequestDTO dto
    ) {
    	System.out.println(dto);
        authService.registerUser(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody LoginRequestDTO loginDto
    ) {
        AuthResponseDTO dto = authService.loginUser(loginDto);
        return ResponseEntity.ok(dto);
    }
}
