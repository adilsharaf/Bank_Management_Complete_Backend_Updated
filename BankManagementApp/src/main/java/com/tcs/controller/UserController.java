package com.tcs.controller;

import com.tcs.dto.RegisterRequestDTO;
import com.tcs.dto.UserResponseDTO;
import com.tcs.service.UserService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // SUPER ADMIN creates ADMIN
    @PostMapping("/super-admin/admin")
    public ResponseEntity<String> createAdmin(
            @RequestBody RegisterRequestDTO dto) {

        userService.createAdmin(dto);
        return ResponseEntity.ok("Admin created successfully");
    }

    // Block User
    @PutMapping("/{userId}/block")
    public ResponseEntity<String> blockUser(
            @PathVariable int userId) {

        userService.blockUser(userId);
        return ResponseEntity.ok("User blocked successfully");
    }

    // Unblock User
    @PutMapping("/{userId}/unblock")
    public ResponseEntity<String> unblockUser(
            @PathVariable int userId) {

        userService.unblockUser(userId);
        return ResponseEntity.ok("User unblocked successfully");
    }

    // Get all users
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }
}

