package com.tcs.controller;

import com.tcs.dto.DepositRequestDTO;
import com.tcs.dto.TransactionRequestDTO;
import com.tcs.dto.TransactionResponseDTO;
import com.tcs.dto.WithdrawRequestDTO;
import com.tcs.entity.Transaction;
import com.tcs.entity.values.TransactionType;
import com.tcs.security.JwtUtil;
import com.tcs.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	JwtUtil jwtUtil;
	
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestHeader("Authorization") String authorizationHeader,@RequestBody TransactionRequestDTO dto) {
    	
    	String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
    	System.out.println("userrole"+userRole);
    	
        service.transfer(userRole,userId,dto);
        return ResponseEntity.ok("Transfer successful");
    }

    @PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@RequestHeader("Authorization") String authorizationHeader,@RequestBody DepositRequestDTO dto) {
    	
    	String token = authorizationHeader.substring(7);  
    	String userRole=(String) jwtUtil.extractRole(token);
    
        service.deposit(userRole,dto);
        return ResponseEntity.ok("Deposit successful");
    }

    @PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestHeader("Authorization") String authorizationHeader,@RequestBody WithdrawRequestDTO dto) {
        
    	String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
    	
    	
    	service.withdraw(userRole,userId,dto);
        return ResponseEntity.ok("Withdraw successful");
    }

    @PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
    @GetMapping("/history/{accountNumber}")
    public ResponseEntity<List<Transaction>> history(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String accountNumber) {
    	String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
   
    	
    	return ResponseEntity.ok(service.history(userRole,userId,accountNumber));
    }
}
