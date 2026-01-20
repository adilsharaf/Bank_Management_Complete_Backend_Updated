package com.tcs.controller;

import com.tcs.dto.EMIPaymentRequestDTO;
import com.tcs.dto.LoanApplyRequestDTO;
import com.tcs.dto.LoanResponseDTO;
import com.tcs.security.JwtUtil;
import com.tcs.service.LoanService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController {
	
	private final JwtUtil jwtUtil;

	private final LoanService loanService;

	public LoanController(LoanService loanService,JwtUtil jwtUtil) {
		this.loanService = loanService;
		this.jwtUtil=jwtUtil;
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@PostMapping("/apply")
	public ResponseEntity<String> applyLoan(@RequestHeader("Authorization") String authorizationHeader,@RequestBody LoanApplyRequestDTO dto) {
		
		System.out.println("Loan called");
        System.out.println(dto);
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		loanService.applyLoan(userRole,userId,dto);
		return ResponseEntity.ok("Loan application submitted");
	}
	

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@PutMapping("/{loanId}/approve")
	public ResponseEntity<String> approveLoan(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String loanId) {
		
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		loanService.approveLoan(userRole,userId,loanId);
		return ResponseEntity.ok("Loan approved");
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@PutMapping("/{loanId}/reject")
	public ResponseEntity<String> rejectLoan(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String loanId) {
		
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		loanService.rejectLoan(userRole,userId,loanId);
		return ResponseEntity.ok("Loan rejected");
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@PutMapping("/emi/{loanId}")
	public ResponseEntity<String> payEMI(@RequestHeader("Authorization") String authorizationHeader,@RequestBody EMIPaymentRequestDTO dto,@PathVariable String loanId) {
		
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);	
		loanService.payEMI(userRole,userId,loanId,dto);
		return ResponseEntity.ok("EMI paid successfully");
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@GetMapping("/{loanId}")
	public ResponseEntity<LoanResponseDTO> getLoanDetails(@RequestHeader("Authorization") String authorizationHeader,@PathVariable String loanId) {
		
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		return ResponseEntity.ok(loanService.getLoanDetails(userRole,userId,loanId));
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<LoanResponseDTO>> getLoansByUser(@RequestHeader("Authorization") String authorizationHeader,@PathVariable int userId) {
		System.out.println("Get loans called"+userId);
		String token = authorizationHeader.substring(7);  
        
    	int user = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		return ResponseEntity.ok(loanService.getLoansByUser(userRole,userId,userId));
	}

	@PreAuthorize("hasRole('ADMIN','SUPER_ADMIN')")
	@GetMapping("/pending")
	public ResponseEntity<List<LoanResponseDTO>> getPendingLoans(@RequestHeader("Authorization") String authorizationHeader) {
		
		String token = authorizationHeader.substring(7);  
        
    	int userId = (int) jwtUtil.extractClaims(token).get("userId");
    	String userRole=(String) jwtUtil.extractRole(token);
		return ResponseEntity.ok(loanService.getPendingLoans(userRole,userId));
	}
}