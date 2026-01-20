package com.tcs.controller;

import com.tcs.dto.AccountCreateRequestDTO;
import com.tcs.dto.AccountResponseDTO;
import com.tcs.security.JwtUtil;
import com.tcs.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	
	@Autowired
    private JwtUtil jwtUtil; 

    @Autowired
    private AccountService accountService;

    // Create a new account
    @PostMapping
    public ResponseEntity<?> createAccount(@RequestHeader("Authorization") String authorizationHeader,@RequestBody AccountCreateRequestDTO accountCreateRequestDTO) {
        System.out.println("Account creater called");
    	String token = authorizationHeader.substring(7);  

       
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	try {
            accountService.createAccount(userId, accountCreateRequestDTO);
            return ResponseEntity.ok("Account created successfully");
        } catch (IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } 
    }

    
    @GetMapping("/getById/{accountNumber}")
    public AccountResponseDTO getAccountDetails(@PathVariable String accountNumber) {
        return accountService.getAccountDetails(accountNumber);
    }

    // Block an account by account number
    @PutMapping("/{accountNumber}/block")
    public String blockAccount(@PathVariable String accountNumber) {
        accountService.blockAccount(accountNumber);
        return "Account blocked successfully";
    }
    
    @PutMapping("/{accountNumber}/unblock")
    public String unBlockAccount(@PathVariable String accountNumber) {
        accountService.unBlockAccount(accountNumber);
        return "Account unblocked successfully";
    }

    // Get all accounts for a specific user
    @GetMapping("/user/{userId}")
    public List<AccountResponseDTO> getAccountsByUser(@RequestHeader("Authorization") String authorizationHeader,@PathVariable int userId) {
    	System.out.println("Account Controller called");
    	String token = authorizationHeader.substring(7);  
        
    	int currentUser = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
        return accountService.getAccountsByUser(currentUser,userRole,userId);
    }
}
